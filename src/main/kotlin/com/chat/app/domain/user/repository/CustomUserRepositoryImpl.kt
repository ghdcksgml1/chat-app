package com.chat.app.domain.user.repository

import com.chat.app.domain.user.collection.UserDocument
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.inValues
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl(
    private val reactiveMongoOperations: ReactiveMongoOperations
) : CustomUserRepository {
    override suspend fun findAllByIdsIn(ids: List<String>): List<UserDocument> {
        val query = Query()
            .addCriteria(UserDocument::id inValues ids)

        return reactiveMongoOperations.find<UserDocument>(query)
            .collectList()
            .awaitSingle()
    }
}