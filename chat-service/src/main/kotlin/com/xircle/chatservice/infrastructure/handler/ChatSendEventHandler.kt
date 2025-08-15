package com.xircle.chatservice.infrastructure.handler

import com.xircle.chatservice.domain.event.SendPrivateChatEvent
import com.xircle.chatservice.domain.integration.reader.ChatReader
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ChatSendEventHandler(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val chatReader: ChatReader
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleSendPrivateChatEvent(event: SendPrivateChatEvent){
        val destination = chatReader.findReceiverDestination(event.receiverId)
        kafkaTemplate.send("${destination}-chat", event)
    }
}