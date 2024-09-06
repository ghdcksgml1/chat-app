package com.chat.app.domain.room.repository

import com.chat.app.domain.room.collection.ChatRoomDocument
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRoomRepository : CoroutineCrudRepository<ChatRoomDocument, String>, CustomChatRoomRepository {
}