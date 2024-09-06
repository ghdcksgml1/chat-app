package com.chat.app.domain.user.jwt

object JwtUtils {
    fun parseBearerToken(bearerToken: String): String =
        bearerToken.substring("bearer ".length)
}