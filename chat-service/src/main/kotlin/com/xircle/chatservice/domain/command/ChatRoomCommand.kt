package com.xircle.chatservice.domain.command

data class ChatRoomCommand(
    val sessionId: String,
    val hostId: String,
    val memberId: String,
    val roomId: String,
    val roomName: String,
)
