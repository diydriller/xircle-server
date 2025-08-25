package com.xircle.followservice.infrastructure.store

import com.xircle.followservice.domain.integration.store.FollowStore
import com.xircle.followservice.domain.model.MemberNode
import com.xircle.followservice.infrastructure.repository.MemberNodeRepository
import org.springframework.stereotype.Component

@Component
class FollowStoreImpl(
    private val memberNodeRepository: MemberNodeRepository
) : FollowStore {
    override fun follow(followerId: String, followeeId: String) {
        memberNodeRepository.follow(followerId, followeeId)
    }

    override fun unfollow(followerId: String, followeeId: String) {
        memberNodeRepository.unfollow(followerId, followeeId)
    }

    override fun saveMember(member: MemberNode) {
        memberNodeRepository.save(member)
    }
}