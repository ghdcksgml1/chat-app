package com.chat.app.domain.room.service

import com.chat.app.config.MongoDBContainerConfig
import com.chat.app.domain.room.ChatRoomDocumentFixture
import com.chat.app.domain.room.dto.CreateChatRoomCommand
import com.chat.app.domain.room.exception.ChatRoomMemberForbiddenException
import com.chat.app.domain.room.exception.ChatRoomNotFoundException
import com.chat.app.domain.room.repository.ChatRoomRepository
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertContains

class ChatRoomServiceTest : MongoDBContainerConfig() {
    @Autowired
    lateinit var chatRoomService: ChatRoomService

    @Autowired
    lateinit var chatRoomRepository: ChatRoomRepository

    @Nested
    inner class `verifyChatRoomMember 메서드는` {

        @Test
        fun `채팅방의 멤버인지 검증한다`() = assertDoesNotThrow {
            runTest {
                // given
                val joinedUserId = "4574"
                val chatRoomDocument =
                    chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방(joinedUserId, listOf(joinedUserId)))

                // when
                chatRoomService.verifyChatRoomMember(chatRoomDocument.id!!, joinedUserId)
            }
        }

        @Test
        fun `채팅방의 멤버가 아닌 경우, 예외를 호출한다`() = assertThrows(ChatRoomMemberForbiddenException::class.java) {
            runTest {
                // given
                val joinedUserId = "4574"
                val doesNotJoinedUserId = "4928123"
                val chatRoomDocument =
                    chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방(joinedUserId, listOf(joinedUserId)))

                // when & then
                chatRoomService.verifyChatRoomMember(chatRoomDocument.id!!, doesNotJoinedUserId)
            }
        }
    }

    @Nested
    inner class `findById 메서드는` {

        @Test
        fun `chatRoomId로 채티방을 찾는다`() = runTest {
            // given
            val joinedUserId = "4574"
            val chatRoomDocument =
                chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방(joinedUserId, listOf(joinedUserId)))

            // when
            val findChatRoom = chatRoomService.findById(chatRoomDocument.id!!)

            // then
            assertEquals(chatRoomDocument.id!!, findChatRoom.id)
        }

        @Test
        fun `존재하지 않는 채팅방인 경우, 예외를 호출한다`() = assertThrows(ChatRoomNotFoundException::class.java) {
            runTest {
                // given
                val notExistChatRoom = "32a0921-239c032-abd2f3"

                // when
                chatRoomService.findById(notExistChatRoom)
            }
        }
    }

    @Test
    fun `findAllUserChatRooms 메서드는 유저가 속해있는 모든 채팅방을 찾는다`() = runTest {
        // given
        val targetUserId = "30429"

        val chatRoomDocument1 = chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방("28318", listOf("28318", targetUserId)))
        val chatRoomDocument2 = chatRoomRepository.save(ChatRoomDocumentFixture.일반_채팅방("932910", listOf("932910", "28318", targetUserId)))

        // when
        val findChatRooms = chatRoomService.findAllUserChatRooms(targetUserId)

        // then
        val expectChatRoomIds = listOf(chatRoomDocument1.id!!, chatRoomDocument2.id!!)
        assertThat(findChatRooms.map { it.id }).containsAnyElementsOf(expectChatRoomIds)
    }

    @Test
    fun `createChatRoom 메서드는 채팅방을 생성한다`() = runTest {
        // given
        val userId = "4574"
        val roomName = "하이룸"
        val roomType = "1:1 채팅방"

        val command = CreateChatRoomCommand(
            roomName,
            roomType
        )

        // when
        val chatRoom = chatRoomService.createChatRoom(userId, command)

        // then
        assertThat(chatRoom).isNotNull
        assertEquals(userId, chatRoom.createdByUserId)
        assertEquals(roomName, chatRoom.roomName)
        assertEquals(roomType, chatRoom.roomType)
    }
}