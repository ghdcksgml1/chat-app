package com.chat.app.domain.room.dto

import com.chat.app.domain.room.collection.ChatRoomMember
import java.time.LocalDateTime

data class ChatRoom(
    val id: String,
    val roomName: String,
    val roomType: String,
    val members: List<ChatRoomMember>,
    val createdByUserId: String,
    val createdAt: LocalDateTime,
) {
    fun isJoinedMember(userId: String): Boolean {
        return members.any { member ->
            member.userId == userId
        }
    }
}
