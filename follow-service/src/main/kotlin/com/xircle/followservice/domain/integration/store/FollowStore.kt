package com.xircle.followservice.domain.integration.store

import com.xircle.followservice.domain.model.MemberNode

interface FollowStore {
    fun follow(followerId: String, followeeId: String)
    fun unfollow(followerId: String, followeeId: String)
    fun saveMember(member: MemberNode)
}