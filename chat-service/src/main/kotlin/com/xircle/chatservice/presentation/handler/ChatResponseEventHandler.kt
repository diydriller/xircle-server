package com.xircle.chatservice.presentation.handler

import com.xircle.chatservice.domain.command.ChatMessageCommand
import com.xircle.chatservice.domain.command.ChatRoomCommand
import com.xircle.chatservice.domain.event.CreatePrivateChatRoomEvent
import com.xircle.chatservice.domain.event.SendPrivateChatEvent
import com.xircle.chatservice.util.SocketUtil.createHeaders
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ChatResponseEventHandler(
    private val messagingTemplate: SimpMessagingTemplate
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleChatMessage(chatMessageCommand: ChatMessageCommand){
        messagingTemplate.convertAndSendToUser(
            chatMessageCommand.sessionId, "/queue/chat",
            SendPrivateChatEvent(
                id = chatMessageCommand.chatMessageId,
                roomId = chatMessageCommand.roomId,
                senderId = chatMessageCommand.senderId,
                message = chatMessageCommand.message,
                receiverId = chatMessageCommand.receiverId
            ), createHeaders(chatMessageCommand.sessionId)
        )
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleChatRoom(chatRoomCommand: ChatRoomCommand){
        messagingTemplate.convertAndSendToUser(
            chatRoomCommand.sessionId, "/queue/room",
            CreatePrivateChatRoomEvent(
                id = chatRoomCommand.roomId,
                name = chatRoomCommand.roomName,
                hostId = chatRoomCommand.hostId,
                memberId = chatRoomCommand.memberId
            ),
            createHeaders(chatRoomCommand.sessionId)
        )
    }
}