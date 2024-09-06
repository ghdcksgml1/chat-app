package com.chat.app.domain.user.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.time.Instant
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object JwtGenerator {
    @OptIn(ExperimentalEncodingApi::class)
    private val algorithm: Algorithm = Algorithm.HMAC256(Base64.Default.decode("secret"))

    fun generate(platformId: String, expireAt: Instant? = Instant.now().plusSeconds(3600)): String {
        return JWT.create()
            .withClaim("platformId", platformId)
            .withExpiresAt(expireAt)
            .withIssuedAt(Instant.now())
            .sign(algorithm);
    }
}