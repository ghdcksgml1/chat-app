package com.chat.app.domain.user.dto

data class ExposureUserInfo(
    val id: String,
    val username: String,
    val profilePicture: String?,
)

val UNKNOWN = ExposureUserInfo(id = "unknown", username = "알 수 없는 사용자", profilePicture = null)