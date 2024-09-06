package com.chat.app.domain.user.jwt

import com.chat.app.config.MongoDBContainerConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class JwtUserDecoderTest : MongoDBContainerConfig() {
    @Autowired lateinit var jwtUserDecoder: JwtUserDecoder

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun `토큰 복호화 테스트`() {
        // given
        val encodeToByteArray = Base64.Default.decode("secret".toByteArray())
        println(encodeToByteArray)
        val platformId = "4574"
        val token: String = JwtGenerator.generate(platformId, Instant.now().plusSeconds(3600))
        println(token)

        // when
        val decodedPlatformId = jwtUserDecoder.getUserId(token)

        // then
        assertEquals(platformId, decodedPlatformId)
    }
}