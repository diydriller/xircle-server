package com.xircle.chatservice.domain.event

data class CreatePrivateChatRoomEvent(
    val id: String,
    val name: String,
    val hostId: String,
    val memberId: String
)