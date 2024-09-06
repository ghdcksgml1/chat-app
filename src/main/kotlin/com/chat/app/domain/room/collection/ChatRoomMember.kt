package com.chat.app.domain.room.collection

import java.time.LocalDateTime

data class ChatRoomMember(
    val userId: String,
    val joinedAt: LocalDateTime = LocalDateTime.now()
)
