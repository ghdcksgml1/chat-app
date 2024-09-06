package com.chat.app.application.chat.presentation

import com.chat.app.domain.message.dto.ChatMessage
import com.chat.app.domain.message.dto.ChatMessageRequest
import com.chat.app.domain.room.dto.ChatRoom
import com.chat.app.application.chat.facade.ChatMessageFacade
import com.chat.app.domain.user.jwt.JwtUtils
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController(
    private val chatMessageFacade: ChatMessageFacade,
) {

    @GetMapping("/chat-rooms/{chatRoomId}/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    suspend fun streamChatRoom(
        @RequestHeader(name = AUTHORIZATION) bearerToken: String,
        @PathVariable chatRoomId: String,
    ): Flow<ChatMessage> {
        val token = JwtUtils.parseBearerToken(bearerToken)
        return chatMessageFacade.streamChatRoom(token, chatRoomId)
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/chat-rooms/{chatRoomId}/send")
    suspend fun sendChatMessage(
        @RequestHeader(name = AUTHORIZATION) bearerToken: String,
        @PathVariable chatRoomId: String,
        @RequestBody request: ChatMessageRequest
    ) {
        val token = JwtUtils.parseBearerToken(bearerToken)
        chatMessageFacade.sendChatMessage(token, chatRoomId, request)
    }

    @GetMapping("/chat-rooms")
    suspend fun findAllChatRooms(
        @RequestHeader(name = AUTHORIZATION) bearerToken: String
    ): List<ChatRoom> {
        val token = JwtUtils.parseBearerToken(bearerToken)
        return chatMessageFacade.findUserChatRooms(token)
    }
}