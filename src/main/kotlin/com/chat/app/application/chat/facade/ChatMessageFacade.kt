package com.chat.app.application.chat.facade

import com.chat.app.domain.message.dto.ChatMessage
import com.chat.app.domain.message.dto.ChatMessageRequest
import com.chat.app.domain.room.dto.ChatRoom
import com.chat.app.domain.message.dto.CreateChatMessageCommand
import com.chat.app.domain.message.service.ChatMessageService
import com.chat.app.domain.room.service.ChatRoomService
import com.chat.app.application.chat.service.ChatRoomStreamer
import com.chat.app.domain.user.service.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.onStart
import org.springframework.stereotype.Service

@Service
class ChatMessageFacade(
    private val userService: UserService,
    private val chatRoomService: ChatRoomService,
    private val chatMessageService: ChatMessageService
) {

    // 채팅방 입장
    suspend fun streamChatRoom(token: String, chatRoomId: String): Flow<ChatMessage> {
        val userInfo = userService.getUserInfo(token)
        chatRoomService.verifyChatRoomMember(chatRoomId, userInfo.id)
        val latestMessage = chatMessageService.getLatestMessage(chatRoomId)

        return ChatRoomStreamer.getChatRoomStream(chatRoomId)
            .onStart {
                emitAll(latestMessage.asFlow())
            }
    }

    // 채팅 보내기
    suspend fun sendChatMessage(token: String, chatRoomId: String, request: ChatMessageRequest) {
        val userInfo = userService.getUserInfo(token)
        chatRoomService.verifyChatRoomMember(chatRoomId, userInfo.id)

        val command = CreateChatMessageCommand(
            chatRoomId = chatRoomId,
            senderId = userInfo.id,
            message = request.message,
            messageType = request.messageType
        )

        val createdChatMessage = chatMessageService.createChatMessage(command)
        ChatRoomStreamer.sendMessage(chatRoomId, createdChatMessage)
    }

    // 유저가 들어가 있는 채팅방
    suspend fun findUserChatRooms(token: String): List<ChatRoom> {
        val userInfo = userService.getUserInfo(token)
        return chatRoomService.findAllUserChatRooms(userInfo.id)
    }
}