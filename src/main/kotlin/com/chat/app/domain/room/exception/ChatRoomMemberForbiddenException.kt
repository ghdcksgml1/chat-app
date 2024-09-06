package com.chat.app.domain.room.exception

class ChatRoomMemberForbiddenException(chatRoomId: String, userId: String) :
    RuntimeException("채팅방에 접근 권한이 없는 유저입니다. chatRoomId = ${chatRoomId}, userId = ${userId}") {
}