package com.chat.app.domain.room.exception

class ChatRoomNotFoundException(chatRoomId: String) :
    RuntimeException("채팅방을 찾지 못했습니다. chatRoomdId = ${chatRoomId}") {
}