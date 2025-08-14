package com.xircle.postservice.domain.integration.reader

import com.xircle.postservice.domain.model.Post

interface PostReader {
    fun findAllPostByMember(page: Int, size: Int, memberId: Long): List<Post>

    fun findAllPostByMemberInterest(page: Int, size: Int, memberId: Long, interest: String): List<Post>

    fun findAllFollowPostByMember(page: Int, size: Int, followerIdList: List<Long>): List<Post>

    fun findAllByIdList(idList: List<Long>): List<Post>

    fun findById(postId: Long): Post

    fun findTop5ByCommentCount(): List<Post>
}