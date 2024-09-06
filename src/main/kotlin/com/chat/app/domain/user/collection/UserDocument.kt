package com.chat.app.domain.user.collection

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "users")
data class UserDocument(
    @Id val id: String,
    val username: String,
    val profilePicture: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now()
)
