package com.xircle.postservice.domain.integration.reader

interface FollowReader {
    fun findAllFollower(followeeId: String): List<String>
}