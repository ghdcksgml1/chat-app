package com.chat.app.domain.message.repository

import com.chat.app.domain.message.collection.ChatMessageDocument
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatMessageRepository : CoroutineCrudRepository<ChatMessageDocument, String>, CustomChatMessageRepository {
}