package com.chat.app.domain.user.repository

import com.chat.app.domain.user.collection.UserDocument
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CoroutineCrudRepository<UserDocument, String>, CustomUserRepository {
}