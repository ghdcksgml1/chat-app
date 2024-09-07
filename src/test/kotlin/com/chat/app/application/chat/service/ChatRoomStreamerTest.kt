package com.chat.app.application.chat.service

import com.chat.app.domain.message.collection.ChatMessageType
import com.chat.app.domain.message.dto.ChatMessage
import com.chat.app.domain.message.dto.ChatMessageReadInfo
import com.chat.app.domain.user.dto.ExposureUserInfo
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ChatRoomStreamerTest {

    @Test
    fun `sendMessage 메서드는 채티방에 연결된 모든 사람들에게 메시지를 전달한다`() = runTest {
        // given
        val chatRoomId = "test chat Room"

        val receivedMessage1 = async { ChatRoomStreamer.getChatRoomStream(chatRoomId).first() }
        val receivedMessage2 = async { ChatRoomStreamer.getChatRoomStream(chatRoomId).first() }
        val receivedMessage3 = async { ChatRoomStreamer.getChatRoomStream(chatRoomId).first() }

        val chatMessageToSend = ChatMessage(
            id = "1",
            sender = ExposureUserInfo("4574", "홍찬희", null),
            message = "안녕하세요",
            messageType = ChatMessageType.TEXT,
            sendAt = LocalDateTime.now(),
            readInfo = ChatMessageReadInfo(1)
        )


        // when
        launch {
            ChatRoomStreamer.sendMessage(chatRoomId, chatMessageToSend)
        }

        // then
        assertEquals(chatMessageToSend, receivedMessage1.await())
        assertEquals(chatMessageToSend, receivedMessage2.await())
        assertEquals(chatMessageToSend, receivedMessage3.await())
    }
}