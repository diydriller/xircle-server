package com.xircle.chatservice.domain.model

import com.github.f4b6a3.tsid.TsidCreator
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "chat_message")
data class ChatMessage(
    @Id
    var id: String = TsidCreator.getTsid().toString(),
    val message: String,
    @Field("room_id")
    val roomId: String,
    @Field("sender_id")
    val senderId: String
) : BaseEntity()