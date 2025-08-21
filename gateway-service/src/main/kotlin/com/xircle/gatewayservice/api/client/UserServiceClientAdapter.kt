package com.xircle.gatewayservice.api.client

import com.xircle.common.dto.MemberInfo
import com.xircle.userservice.application.grpc.proto.GetMemberProfileRequest
import com.xircle.userservice.application.grpc.proto.ReactorGetMemberProfileServiceGrpc
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserServiceClientAdapter(
    @GrpcClient("local-grpc-server")
    private val reactorGetMemberProfileServiceStub: ReactorGetMemberProfileServiceGrpc.ReactorGetMemberProfileServiceStub
) {
    fun getMemberInfo(memberId: Long): Mono<MemberInfo> {
        val request = GetMemberProfileRequest.newBuilder()
            .setUserId(memberId)
            .build()

        val response = reactorGetMemberProfileServiceStub.getMemberProfileCall(request)
            .map { response ->
                val info = response.info
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