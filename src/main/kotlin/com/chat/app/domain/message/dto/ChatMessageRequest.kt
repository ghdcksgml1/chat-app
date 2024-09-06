package com.chat.app.domain.message.dto

import com.chat.app.domain.message.collection.ChatMessageType

data class ChatMessageRequest(
    val message: String,
    val messageType: ChatMessageType
)
