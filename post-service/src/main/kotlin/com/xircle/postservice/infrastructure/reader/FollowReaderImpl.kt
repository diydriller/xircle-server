package com.xircle.postservice.infrastructure.reader

import com.xircle.followservice.application.grpc.proto.GetFollowerRequest
import com.xircle.followservice.application.grpc.proto.GetFollowerServiceGrpc
import com.xircle.postservice.domain.integration.reader.FollowReader
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component

@Component
class FollowReaderImpl(
    @GrpcClient("local-grpc-server")
    private val getFollowerServiceBlockingStub: GetFollowerServiceGrpc.GetFollowerServiceBlockingStub
) : FollowReader {
    override fun findAllFollower(followeeId: Long): List<Long> {
        val request = GetFollowerRequest.newBuilder()
            .setUserId(followeeId)
            .build()

        return getFollowerServiceBlockingStub.getFollowerCall(request).userIdList
    }
}