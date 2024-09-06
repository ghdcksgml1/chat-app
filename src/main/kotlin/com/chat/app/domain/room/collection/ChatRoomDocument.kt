package com.chat.app.domain.room.collection

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

// db.chat_rooms.createIndex({ "members.userId": 1 })
@Document(collection = "chat_rooms")
data class ChatRoomDocument(
    @Id val id: String? = null,
    val roomName: String,
    val roomType: String,
    val members: List<ChatRoomMember> = listOf(),
    val createdByUserId: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)

