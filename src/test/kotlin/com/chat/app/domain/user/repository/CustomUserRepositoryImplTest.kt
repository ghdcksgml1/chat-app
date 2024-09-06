package com.chat.app.domain.user.repository

import com.chat.app.config.MongoDBContainerConfig
import com.chat.app.domain.user.UserDocumentFixture
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CustomUserRepositoryImplTest : MongoDBContainerConfig() {
    @Autowired lateinit var userRepository: UserRepository

    @Test
    fun `in 쿼리 테스트`() = runTest {
        // given
        val save = userRepository.save(UserDocumentFixture.일반_사용자())

        // when
        val findUser = userRepository.findAllByIdsIn(listOf(save.id))

        // then
        assertEquals(1, findUser.size)
        assertEquals(save.id, findUser[0].id)
    }
}