package com.chat.app.domain.room.service

import com.chat.app.domain.room.collection.ChatRoomDocument
import com.chat.app.domain.room.collection.ChatRoomMember
import com.chat.app.domain.room.dto.ChatRoom
import com.chat.app.domain.room.dto.CreateChatRoomCommand
import com.chat.app.domain.room.exception.ChatRoomMemberForbiddenException
import com.chat.app.domain.room.exception.ChatRoomNotFoundException
import com.chat.app.domain.room.mapper.ChatRoomMapper
import com.chat.app.domain.room.repository.ChatRoomRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository
) {
    suspend fun verifyChatRoomMember(chatRoomId: String, userId: String) {
        val chatRoom = findById(chatRoomId)
        if (!chatRoom.isJoinedMember(userId)) {
            throw ChatRoomMemberForbiddenException(chatRoomId, userId)
        }
    }

    suspend fun findById(chatRoomId: String): ChatRoom {
        return chatRoomRepository.findById(chatRoomId)
            ?.let(ChatRoomMapper::mapToDomain)
            ?: throw ChatRoomNotFoundException(chatRoomId)
    }

    suspend fun findAllUserChatRooms(userId: String): List<ChatRoom> {
        return chatRoomRepository.findAllByUserId(userId)
            .map(ChatRoomMapper::mapToDomain)
    }

    suspend fun createChatRoom(createUserId: String, command: CreateChatRoomCommand): ChatRoom {
        val chatRoomDocument = ChatRoomDocument(
            roomName = command.roomName,
            roomType = command.roomType,
            members = listOf(ChatRoomMember(createUserId, LocalDateTime.now())),
            createdByUserId = createUserId
        )
        return chatRoomRepository.save(chatRoomDocument)
            .let(ChatRoomMapper::mapToDomain)
    }
}