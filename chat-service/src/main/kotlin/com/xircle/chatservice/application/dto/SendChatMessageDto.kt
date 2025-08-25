package com.xircle.chatservice.application.dto

data class SendChatMessageDto(
    val roomId: String,
    val sessionId: String,
    val senderId: String,
    val message: String,
    val receiverId: String
)