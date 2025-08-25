package com.xircle.chatservice.infrastructure.api.fallback

import com.xircle.chatservice.infrastructure.api.client.UserServiceClient
import com.xircle.common.dto.MemberInfo
import org.springframework.cloud.openfeign.FallbackFactory
import org.springframework.stereotype.Component

@Component
class UserServiceClientFallbackFactory : FallbackFactory<UserServiceClient> {
    override fun create(cause: Throwable): UserServiceClient {
        return object : UserServiceClient {
            override fun getMemberInfo(memberId: String): MemberInfo? {
                return null
            }

            override fun getMemberInfoList(memberIdList: List<String>): List<MemberInfo> {
                return emptyList()
            }
        }
    }
}