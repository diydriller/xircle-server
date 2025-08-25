package com.xircle.userservice.application.grpc

import com.xircle.userservice.application.grpc.proto.*
import com.xircle.userservice.domain.integration.reader.MemberReader
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class GetMemberProfileServiceImpl(
    private val memberReader: MemberReader
) : GetMemberProfileServiceGrpc.GetMemberProfileServiceImplBase() {
    override fun getMemberProfileListCall(
        request: GetMemberProfileListRequest,
        responseObserver: StreamObserver<GetMemberProfileListResponse>
    ) {
        val memberInfoList = memberReader.findMemberByIdListIn(request.userIdList).map { member ->
            GetMemberProfileInfo.newBuilder()
                .setProfileImage(member.profileImage)
                .setId(member.id)
                .setNickname(member.nickname)
                .setEmail(member.email)
                .build()
        }

        val response = GetMemberProfileListResponse.newBuilder()
            .addAllInfo(memberInfoList)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    override fun getMemberProfileCall(
        request: GetMemberProfileRequest,
        responseObserver: StreamObserver<GetMemberProfileResponse>
    ) {
        val member = memberReader.findMemberById(request.userId)
        val memberInfo = GetMemberProfileInfo.newBuilder()
            .setProfileImage(member.profileImage)
            .setId(member.id)
            .setNickname(member.nickname)
            .setEmail(member.email)
            .build()

        val response = GetMemberProfileResponse.newBuilder()
            .setInfo(memberInfo)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}