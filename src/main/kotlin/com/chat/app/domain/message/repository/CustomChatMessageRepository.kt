package com.chat.app.domain.message.repository

import com.chat.app.domain.message.collection.ChatMessageDocument

interface CustomChatMessageRepository {
    suspend fun getLatestChatMessage(chatRoomId: String, count: Int, cursorId: String?): List<ChatMessageDocument>
    suspend fun createChatMessage(document: ChatMessageDocument): ChatMessageDocument
}