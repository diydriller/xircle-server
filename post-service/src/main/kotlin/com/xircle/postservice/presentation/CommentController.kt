package com.xircle.postservice.presentation

import com.xircle.common.response.BaseResponse
import com.xircle.postservice.application.CommentService
import com.xircle.postservice.mapper.CreateCommentMapper
import com.xircle.postservice.presentation.dto.CommentResponse
import com.xircle.postservice.presentation.dto.CreateCommentRequest
import org.springframework.web.bind.annotation.*

@RequestMapping("/post-service")
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("/post/{postId}/comment")
    fun createComment(
        @RequestBody request: CreateCommentRequest,
        @PathVariable postId: String,
        @RequestHeader memberId: String
    ): BaseResponse<CommentResponse> {
        val commentDto = CreateCommentMapper.INSTANCE.covertToDto(request)
        val comment = commentService.createComment(postId, memberId, commentDto)
        val response = CommentResponse.fromEntity(comment)
        return BaseResponse(response)
    }

    @GetMapping("/post/{postId}/comment")
    fun getComment(
        @PathVariable postId: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): BaseResponse<List<CommentResponse>> {
        val response = commentService.getComment(page, size, postId)
            .map { comment -> CommentResponse.fromEntity(comment) }
        return BaseResponse(response)
    }

    @PostMapping("/post/{postId}/comment/{commentId}")
    fun createRecomment(
        @PathVariable postId: String,
        @PathVariable commentId: String,
        @RequestBody request: CreateCommentRequest,
        @RequestHeader memberId: String
    ): BaseResponse<CommentResponse> {
        val commentDto = CreateCommentMapper.INSTANCE.covertToDto(request)
        val comment = commentService.createRecomment(postId, commentId, memberId, commentDto)
        val response = CommentResponse.fromEntity(comment)
        return BaseResponse(response)
    }

    @GetMapping("/post/{postId}/comment/{commentId}")
    fun getRecomment(
        @PathVariable postId: String,
        @PathVariable commentId: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): BaseResponse<List<CommentResponse>> {
        val response = commentService.getRecomment(page, size, postId, commentId)
            .map { comment -> CommentResponse.fromEntity(comment) }
        return BaseResponse(response)
    }
}