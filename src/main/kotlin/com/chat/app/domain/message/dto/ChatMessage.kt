package com.chat.app.domain.message.dto

import com.chat.app.domain.message.collection.ChatMessageType
import com.chat.app.domain.user.dto.ExposureUserInfo
import java.time.LocalDateTime

data class ChatMessage(
    val id: String? = null,
    val sender: ExposureUserInfo,
    val message: String,
    val messageType: ChatMessageType,
    val sendAt: LocalDateTime,
    val readInfo: ChatMessageReadInfo
)