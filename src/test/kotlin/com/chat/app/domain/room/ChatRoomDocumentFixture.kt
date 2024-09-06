package com.chat.app.domain.room

import com.chat.app.domain.room.collection.ChatRoomDocument
import com.chat.app.domain.room.collection.ChatRoomMember

object ChatRoomDocumentFixture {
    fun 일반_채팅방(
        createdByUserId: String,
        roomMemberIdList: List<String>
    ) = ChatRoomDocument(
        roomName = "일반 채팅방",
        roomType = "1:1 채팅방",
        members = roomMemberIdList.map { ChatRoomMember(it) },
        createdByUserId = createdByUserId
    )
}