package com.chat.app.domain.room.repository

import com.chat.app.config.MongoDBContainerConfig
import com.chat.app.domain.room.ChatRoomDocumentFixture
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class CustomChatRoomRepositoryImplTest : MongoDBContainerConfig() {
    @Autowired lateinit var chatRoomRepository: ChatRoomRepository

    @Nested
    inner class `findById 메서드는` {
        @Test
        fun `id로 채팅방을 찾는다`() = runTest {
            // given
            val userId = "45744574"
            val chatRoomDocument = chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방(userId, listOf(userId)))

            // when
            val findChatRoom = chatRoomRepository.findById(chatRoomDocument.id!!)

            // then
            assertEquals(findChatRoom?.id!!, chatRoomDocument.id!!)
        }

        @Test
        fun `id에 맞는 채팅방이 존재하지 않는 경우, null이다`() = runTest {
            // given
            val chatRoomId = "45744574"

            // when
            val findChatRoom = chatRoomRepository.findById(chatRoomId)

            // then
            assertNull(findChatRoom)
        }
    }

    @Test
    fun `findAllByUserId 메서드는 유저가 속해있는 모든 채팅방을 찾는다`() = runTest {
        // given
        val targetUserId = "30429"

        val chatRoomDocument1 = chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방("28318", listOf("28318", targetUserId)))
        val chatRoomDocument2 = chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방("932910", listOf("932910", "28318", targetUserId)))

        // when
        val findChatRooms = chatRoomRepository.findAllByUserId(targetUserId)

        // then
        val expectChatRoomIds = listOf(chatRoomDocument1.id!!, chatRoomDocument2.id!!)
        Assertions.assertThat(findChatRooms.map { it.id }).containsAnyElementsOf(expectChatRoomIds)
    }
}