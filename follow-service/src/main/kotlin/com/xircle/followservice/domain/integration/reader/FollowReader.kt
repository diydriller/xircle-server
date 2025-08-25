package com.xircle.followservice.domain.integration.reader

import com.xircle.followservice.domain.model.MemberNode

interface FollowReader {
    fun findFollowerList(followeeId: String): List<MemberNode>
    fun findFolloweeList(followerId: String): List<MemberNode>
    fun existsFollowRelation(followerId: String, followeeId: String): Boolean
    fun existsMember(memberId: String): Boolean
    fun recommendFollowerOfFollower(memberId: String): List<MemberNode>
}