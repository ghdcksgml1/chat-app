package com.chat.app.domain.user.dto

data class CreateUserCommand(
    val id: String,
    val username: String,
    val profilePicture: String? = null
)