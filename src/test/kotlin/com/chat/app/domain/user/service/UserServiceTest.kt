package com.chat.app.domain.user.service

import com.chat.app.config.MongoDBContainerConfig
import com.chat.app.domain.user.UserDocumentFixture
import com.chat.app.domain.user.dto.CreateUserCommand
import com.chat.app.domain.user.jwt.JwtGenerator
import com.chat.app.domain.user.repository.UserRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertEquals

class UserServiceTest : MongoDBContainerConfig() {
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var userRepository: UserRepository


    @Test
    fun `유저 정보를 가져온다`() = runTest {
        // given
        val savedUser = userRepository.save(UserDocumentFixture.일반_사용자())
        val token = JwtGenerator.generate(savedUser.id)

        // when
        val userInfo = userService.getUserInfo(token)

        // then
        assertEquals(savedUser.id, userInfo.id)
    }

    @Test
    fun `유저가 존재하지 않으면 생성한다`() = runTest {
        // given
        val userId = "45721"
        val token = JwtGenerator.generate(userId)

        // when
        val userInfo = userService.getUserInfo(token)

        // then
        assertEquals(userId, userInfo.id)
    }
}