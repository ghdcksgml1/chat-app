package com.chat.app.application.chat.service

import com.chat.app.domain.message.dto.ChatMessage
import kotlinx.coroutines.flow.*
import java.util.concurrent.ConcurrentHashMap

object ChatRoomStreamer {
    private val chatRoomStream: ConcurrentHashMap<String, MutableSharedFlow<ChatMessage>> = ConcurrentHashMap()

    fun getChatRoomStream(chatRoomId: String): Flow<ChatMessage> {
        return chatRoomStream.getOrPut(chatRoomId) { MutableSharedFlow() }
    }

    suspend fun sendMessage(chatRoomId: String, chatMessage: ChatMessage) {
        chatRoomStream[chatRoomId]?.emit(chatMessage)
    }
}