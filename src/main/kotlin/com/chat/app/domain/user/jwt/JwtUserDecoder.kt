package com.chat.app.domain.user.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

private lateinit var decodedSecretKey: ByteArray
private const val PLATFORM_ID = "platformId"

@Service
@OptIn(ExperimentalEncodingApi::class)
class JwtUserDecoder(
    @Value("\${jwt.secretKey}") secretKey: String,
) {
    init {
        decodedSecretKey = Base64.Default.decode(secretKey)
    }

    fun getUserId(token: String): String {
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(decodedSecretKey)
            val verifier: JWTVerifier = JWT.require(algorithm).build()
            val verify: DecodedJWT = verifier.verify(token)
            return verify.claims.getValue(PLATFORM_ID).asString()
        } catch (exception: JWTVerificationException) {
            throw exception
        }
    }
}