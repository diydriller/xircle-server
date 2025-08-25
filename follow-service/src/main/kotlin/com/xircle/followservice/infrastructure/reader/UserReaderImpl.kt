package com.xircle.followservice.infrastructure.reader

import com.xircle.common.dto.MemberInfo
import com.xircle.followservice.domain.integration.reader.UserReader
import com.xircle.userservice.application.grpc.proto.GetMemberProfileListRequest
import com.xircle.userservice.application.grpc.proto.GetMemberProfileServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component

@Component
class UserReaderImpl(
    @GrpcClient("local-grpc-server")
    private val getMemberProfileServiceBlockingStub: GetMemberProfileServiceGrpc.GetMemberProfileServiceBlockingStub
) : UserReader {
    override fun getMemberInfoList(memberIdList: List<String>): List<MemberInfo> {
        val request = GetMemberProfileListRequest.newBuilder()
            .addAllUserId(memberIdList)
            .build()

        val response = getMemberProfileServiceBlockingStub.getMemberProfileListCall(request).infoList.map { info ->
            MemberInfo(
                id = info.id,
                email = info.email,
                profileImage = info.profileImage,
                nickname = info.nickname
            )
        }
        return response
    }
}