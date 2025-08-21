package com.xircle.followservice.application.grpc

import com.xircle.followservice.application.grpc.proto.GetFollowerRequest
import com.xircle.followservice.application.grpc.proto.GetFollowerResponse
import com.xircle.followservice.application.grpc.proto.GetFollowerServiceGrpc
import com.xircle.followservice.domain.integration.reader.FollowReader
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class GetFollowerServiceImpl(
    private val followerReader: FollowReader
) : GetFollowerServiceGrpc.GetFollowerServiceImplBase() {
    override fun getFollowerCall(request: GetFollowerRequest, responseObserver: StreamObserver<GetFollowerResponse>) {
        val followerList = followerReader.findFollowerList(request.userId)
            .map { memberNode ->
                memberNode.id
            }

        val response = GetFollowerResponse.newBuilder()
            .addAllUserId(followerList)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}