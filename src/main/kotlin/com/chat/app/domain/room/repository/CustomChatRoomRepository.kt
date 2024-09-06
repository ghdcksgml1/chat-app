package com.chat.app.domain.room.repository

import com.chat.app.domain.room.collection.ChatRoomDocument

interface CustomChatRoomRepository {
    suspend fun findById(id: String): ChatRoomDocument?
    suspend fun findAllByUserId(userId: String): List<ChatRoomDocument>
    suspend fun createChatRoom(document: ChatRoomDocument): ChatRoomDocument
}