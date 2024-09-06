package com.chat.app.domain.room.mapper

import com.chat.app.domain.room.collection.ChatRoomDocument
import com.chat.app.domain.room.dto.ChatRoom

object ChatRoomMapper {
    fun mapToDomain(document: ChatRoomDocument): ChatRoom = with(document) {
            ChatRoom(
                id = id!!,
                roomName = roomName,
                roomType = roomType,
                members = members,
                createdByUserId = createdByUserId,
                createdAt = createdAt
            )
        }
}