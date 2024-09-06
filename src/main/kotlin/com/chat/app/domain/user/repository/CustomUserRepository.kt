package com.chat.app.domain.user.repository

import com.chat.app.domain.user.collection.UserDocument

interface CustomUserRepository {
    suspend fun findAllByIdsIn(ids: List<String>): List<UserDocument>
}