package com.xircle.chatservice.domain.command

data class ChatMessageCommand(
    val roomId: String,
    val sessionId: String,
    val senderId: String,
    val message: String,
    val receiverId: String,
    val chatMessageId: String
)
