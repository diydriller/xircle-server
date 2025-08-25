package com.xircle.chatservice.domain.event

data class SendPrivateChatEvent(
    val id: String,
    val roomId: String,
    val senderId: String,
    val receiverId: String,
    val message: String
)
