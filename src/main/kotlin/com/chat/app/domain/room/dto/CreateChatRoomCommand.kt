package com.chat.app.domain.room.dto

data class CreateChatRoomCommand(
    val roomName: String,
    val roomType: String,
)
