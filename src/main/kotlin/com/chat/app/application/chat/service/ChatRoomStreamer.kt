package com.chat.app.application.chat.service

import com.chat.app.domain.message.dto.ChatMessage
import com.chat.app.domain.room.exception.ChatRoomNotFoundException
import kotlinx.coroutines.flow.*
import java.util.concurrent.ConcurrentHashMap

object ChatRoomStreamer {
    private val chatRoomStream: ConcurrentHashMap<String, MutableSharedFlow<ChatMessage>> = ConcurrentHashMap()

    fun getChatRoomStream(chatRoomId: String): Flow<ChatMessage> {
        return chatRoomStream.getOrPut(chatRoomId) { MutableSharedFlow() }
            .asSharedFlow()
    }

    suspend fun sendMessage(chatRoomId: String, chatMessage: ChatMessage) {
        chatRoomStream[chatRoomId]?.emit(chatMessage)
            ?: throw ChatRoomNotFoundException("메시지를 전송할 채팅방이 존재하지 않습니다. chatRoomId = ${chatRoomId}")
    }
}