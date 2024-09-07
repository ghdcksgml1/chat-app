package com.chat.app.domain.message.service

import com.chat.app.config.MongoDBContainerConfig
import com.chat.app.domain.message.ChatMessageDocumentFixture
import com.chat.app.domain.message.repository.ChatMessageRepository
import com.chat.app.domain.room.ChatRoomDocumentFixture
import com.chat.app.domain.room.repository.ChatRoomRepository
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ChatMessageServiceTest : MongoDBContainerConfig() {
    @Autowired
    lateinit var chatRoomRepository: ChatRoomRepository
    @Autowired
    lateinit var chatMessageService: ChatMessageService
    @Autowired
    lateinit var chatMessageRepository: ChatMessageRepository

    @Test
    fun `getLatestChatMessage 메서드는 채팅방의 최근에 받은 메시지를 리턴한다`() = runTest {
        // given
        val userId = "43472-abd322"
        val chatRoomDocument = chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방(userId, listOf(userId)))

        val messageList = listOf("안녕하세요.", "하이요")
        val messageDocumentList = messageList.map {
            chatMessageRepository.save(
                ChatMessageDocumentFixture.메시지(
                    chatRoomId = chatRoomDocument.id!!,
                    userId = userId,
                    message = it
                )
            )
        }

        // when
        val latestMessage = chatMessageService.getLatestMessage(chatRoomDocument.id!!)

        // then
        assertThat(latestMessage.map { it.id!! }).containsExactlyElementsOf(messageDocumentList.map { it.id!! })
    }
}