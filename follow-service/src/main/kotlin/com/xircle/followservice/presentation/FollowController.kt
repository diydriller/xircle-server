package com.xircle.followservice.presentation

import com.xircle.common.dto.MemberInfo
import com.xircle.common.response.BaseResponse
import com.xircle.followservice.application.FollowService
import jakarta.validation.constraints.Min
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/follow-service")
@RestController
class FollowController(
    private val followService: FollowService
) {
    @PatchMapping("/follow/{memberId}")
    fun followMember(
        @Min(value = 1, message = "id must greater than 0")
        @PathVariable(name = "memberId") followeeId: String,
        @RequestHeader(name = "memberId") followerId: String
    ): ResponseEntity<BaseResponse<Boolean>> {
        val isFollow = followService.toggleFollowMember(followerId, followeeId)
        return ResponseEntity.ok().body(BaseResponse(isFollow))
    }

    @GetMapping("/follower")
    fun getFollower(
        @RequestHeader(name = "memberId") followeeId: String
    ): List<String> {
        return followService.getFollower(followeeId)
            .map { memberNode ->
                memberNode.id
            }
    }

    @GetMapping("/following-of-following/{memberId}")
    fun getRecommendation(
        @PathVariable(name = "memberId") memberId: String,
    ): ResponseEntity<BaseResponse<List<MemberInfo>>> {
        val memberInfoList = followService.recommendFor(memberId)
        return ResponseEntity.ok().body(BaseResponse(memberInfoList))
    }
}