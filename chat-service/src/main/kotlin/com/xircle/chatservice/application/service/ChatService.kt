package com.xircle.chatservice.application.service

import com.xircle.chatservice.application.dto.CreateChatRoomDto
import com.xircle.chatservice.application.dto.GetChatMessageDto
import com.xircle.chatservice.application.dto.GetChatRoomDto
import com.xircle.chatservice.application.dto.SendChatMessageDto
import com.xircle.chatservice.domain.command.ChatMessageCommand
import com.xircle.chatservice.domain.command.ChatRoomCommand
import com.xircle.chatservice.domain.event.SendPrivateChatEvent
import com.xircle.chatservice.domain.integration.publisher.ChatPublisher
import com.xircle.chatservice.domain.integration.publisher.MessagePublisher
import com.xircle.chatservice.domain.integration.reader.ChatReader
import com.xircle.chatservice.domain.integration.store.ChatStore
import com.xircle.chatservice.domain.model.ChatMessage
import com.xircle.chatservice.domain.model.ChatRoom
import com.xircle.chatservice.infrastructure.api.client.UserServiceClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ChatService(
    private val chatStore: ChatStore,
    private val chatReader: ChatReader,
    private val chatPublisher: ChatPublisher,
    private val messagePublisher: MessagePublisher,
    private val userServiceClient: UserServiceClient
) {
    @Transactional
    fun createRoom(createChatRoomDto: CreateChatRoomDto): ChatRoom {
        val hostChatRoom = ChatRoom(
            name = createChatRoomDto.name,
            userId = createChatRoomDto.hostId
        )
        chatStore.saveChatRoom(hostChatRoom)

        val memberChatRoom = ChatRoom(
            name = createChatRoomDto.name,
            userId = createChatRoomDto.memberId
        )
        chatStore.saveChatRoom(memberChatRoom)

        chatPublisher.publishCreatedChatRoom(
            ChatRoomCommand(
                sessionId = createChatRoomDto.sessionId,
                hostId = createChatRoomDto.hostId,
                memberId = createChatRoomDto.memberId,
                roomId = hostChatRoom.id!!,
                roomName = memberChatRoom.name
            )
        )
        return hostChatRoom
    }

    @Transactional
    fun chat(sendChatMessageDto: SendChatMessageDto): ChatMessage {
        val chatRoom = chatReader.findChatRoom(sendChatMessageDto.roomId, sendChatMessageDto.senderId)
        val chatMessage = ChatMessage(
            message = sendChatMessageDto.message,
            roomId = chatRoom.id!!,
            senderId = sendChatMessageDto.senderId
        )
        chatStore.saveChatMessage(chatMessage)
        chatRoom.lastMessageId = chatMessage.id
        chatStore.updateChatRoom(chatRoom)

        chatPublisher.publishChatMessage(
            ChatMessageCommand(
                roomId = sendChatMessageDto.roomId,
                sessionId = sendChatMessageDto.sessionId,
                senderId = sendChatMessageDto.senderId,
                message = sendChatMessageDto.message,
                receiverId = sendChatMessageDto.receiverId,
                chatMessageId = chatMessage.id!!
            )
        )

        val destination = chatReader.findReceiverDestination(sendChatMessageDto.receiverId)

        messagePublisher.publish(
            "${destination}-chat",
            SendPrivateChatEvent(
                id = chatMessage.id!!,
                roomId = sendChatMessageDto.roomId,
                senderId = sendChatMessageDto.senderId,
                message = sendChatMessageDto.message,
                receiverId = sendChatMessageDto.receiverId
            )
        )
        return chatMessage
    }

    fun getChatRoom(memberId: Long, lastMessageId: String?, size: Int): List<GetChatRoomDto> {
        val chatRoomList = chatReader.findAllChatRoom(memberId, lastMessageId, size)

        val unreadChatMessageCountList =
            chatReader.findAllUnreadChatMessageCountInEveryRoom(chatRoomList, memberId)
        val unreadChatMessageCountMap = unreadChatMessageCountList.associateBy { it.roomId }

        val lastMessageList = chatReader.findAllLastChatMessageInEveryRoom(chatRoomList)
        val lastMessageMap = lastMessageList.associateBy { lastMessage -> lastMessage.roomId }

        return chatRoomList.map { chatRoom ->
            val lastMessage = lastMessageMap[chatRoom.id]?.message
            val unreadChatMessageCount = unreadChatMessageCountMap[chatRoom.id]?.unreadCount ?: 0

            GetChatRoomDto(
                roomId = chatRoom.id!!,
                name = chatRoom.name,
                unreadCount = unreadChatMessageCount,
                lastReadMessageId = chatRoom.lastReadMessageId,
                lastMessage = lastMessage
            )
        }
    }

    fun getChatMessage(roomId: String, lastMessageId: String?, size: Int): List<GetChatMessageDto> {
        val chatMessageList = chatReader.findAllChatMessage(roomId, lastMessageId, size)

        val memberIdList = chatMessageList.map { it.senderId }
        val memberInfoList = userServiceClient.getMemberInfoList(memberIdList)
        val memberInfoMap = memberInfoList.associateBy { it.id }

        return chatMessageList.map {
            val memberInfo = memberInfoMap[it.senderId]
            GetChatMessageDto(
                id = it.id,
                message = it.message,
                roomId = it.roomId,
                senderId = it.senderId,
                profileImage = memberInfo!!.profileImage,
                nickname = memberInfo.nickname,
                createdAt = it.createdAt
            )
        }
    }

    fun readChat(memberId: Long, roomId: String, lastReadMessageId: String) {
        val chatRoom = chatReader.findChatRoom(roomId, memberId)
        chatRoom.lastReadMessageId = lastReadMessageId
        chatRoom.lastReadMessageTime = LocalDateTime.now()
    }
}