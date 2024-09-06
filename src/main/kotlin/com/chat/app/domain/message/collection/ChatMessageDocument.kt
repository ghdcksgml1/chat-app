package com.chat.app.domain.message.collection

import com.chat.app.domain.room.collection.ChatRoomMember
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "chat_messages")
data class ChatMessageDocument(
    @Id val id: String? = null,
    val chatRoomId: String, // chat_room_id
    val senderId: String, // user_id
    val message: String,
    val messageType: ChatMessageType = ChatMessageType.TEXT,
    val sendAt: LocalDateTime = LocalDateTime.now(),
    val readBy: List<ChatRoomMember>,
)


