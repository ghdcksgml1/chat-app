package com.chat.app.domain.message.service

import com.chat.app.domain.message.collection.ChatMessageDocument
import com.chat.app.domain.room.collection.ChatRoomMember
import com.chat.app.domain.message.dto.ChatMessage
import com.chat.app.domain.message.dto.CreateChatMessageCommand
import com.chat.app.domain.message.mapper.ChatMessageMapper
import com.chat.app.domain.message.repository.ChatMessageRepository
import com.chat.app.domain.user.dto.ExposureUserInfo
import com.chat.app.domain.user.dto.UNKNOWN
import com.chat.app.domain.user.service.UserService
import org.springframework.stereotype.Service

const val LATEST_MESSAGE_COUNT: Int = 10

@Service
class ChatMessageService(
    private val userService: UserService,
    private val chatMessageRepository: ChatMessageRepository
) {

    suspend fun getLatestMessage(chatRoomId: String): List<ChatMessage> {
        val latestChatMessage = chatMessageRepository.getLatestChatMessage(chatRoomId, LATEST_MESSAGE_COUNT, null)

        val senderInfoMap = latestChatMessage.map { it.senderId }.distinct()
            .let { userService.findAllUserInfoIn(it) }
            .let { exposureInfos -> buildMap { exposureInfos.forEach { put(it.id, it) } } }

        return latestChatMessage.map {
            val sender: ExposureUserInfo = senderInfoMap[it.senderId] ?: UNKNOWN
            ChatMessageMapper.mapToDomain(it, sender)
        }
    }

    suspend fun createChatMessage(command: CreateChatMessageCommand): ChatMessage {
        return with(command) {
            ChatMessageDocument(
                chatRoomId = chatRoomId,
                senderId = senderId,
                message = message,
                messageType = messageType,
                readBy = listOf(ChatRoomMember(senderId))
            )
        }.let {
            chatMessageRepository.createChatMessage(it)
        }.let {
            val sender: ExposureUserInfo = userService.findById(it.senderId)
            ChatMessageMapper.mapToDomain(it, sender)
        }
    }
}