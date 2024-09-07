package com.chat.app.domain.message

import com.chat.app.domain.message.collection.ChatMessageDocument
import com.chat.app.domain.room.collection.ChatRoomMember

object ChatMessageDocumentFixture {
    fun 메시지(
        chatRoomId: String,
        userId: String,
        message: String
    ) = ChatMessageDocument(
        chatRoomId = chatRoomId,
        senderId = userId,
        message = message,
        readBy = listOf(ChatRoomMember(userId))
    )
}