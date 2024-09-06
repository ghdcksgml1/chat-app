package com.chat.app.domain.message.dto

import com.chat.app.domain.message.collection.ChatMessageType

data class CreateChatMessageCommand(
    val chatRoomId: String,
    val senderId: String,
    val message: String,
    val messageType: ChatMessageType = ChatMessageType.TEXT,
)
