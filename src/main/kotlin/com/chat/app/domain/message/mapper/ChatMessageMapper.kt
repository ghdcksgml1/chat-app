package com.chat.app.domain.message.mapper

import com.chat.app.domain.message.collection.ChatMessageDocument
import com.chat.app.domain.message.dto.ChatMessage
import com.chat.app.domain.message.dto.ChatMessageReadInfo
import com.chat.app.domain.user.dto.ExposureUserInfo

object ChatMessageMapper {

    fun mapToDomain(document: ChatMessageDocument, user: ExposureUserInfo): ChatMessage = with(document) {
        ChatMessage(
            id = id,
            sender = user,
            message = message,
            messageType = messageType,
            sendAt = sendAt,
            readInfo = ChatMessageReadInfo(readCount = readBy.size)
        )
    }
}