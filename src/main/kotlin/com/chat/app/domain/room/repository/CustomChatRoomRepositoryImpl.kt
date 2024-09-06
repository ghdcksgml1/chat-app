package com.chat.app.domain.room.repository

import com.chat.app.domain.room.collection.ChatRoomDocument
import com.chat.app.domain.room.collection.ChatRoomMember
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Repository

@Repository
class CustomChatRoomRepositoryImpl(
    private val reactiveMongoOperations: ReactiveMongoOperations
) : CustomChatRoomRepository {
    override suspend fun findById(id: String): ChatRoomDocument? {
        val query = Query()
            .addCriteria(ChatRoomDocument::id isEqualTo id)

        return reactiveMongoOperations.find<ChatRoomDocument>(query)
            .awaitFirstOrNull()
    }

    override suspend fun findAllByUserId(userId: String): List<ChatRoomDocument> {
        val query = Query()
            .addCriteria(Criteria("${ChatRoomDocument::members.name}.${ChatRoomMember::userId.name}").`is`(userId))

        return reactiveMongoOperations.find<ChatRoomDocument>(query)
            .collectList()
            .awaitSingle()
    }

    override suspend fun createChatRoom(document: ChatRoomDocument): ChatRoomDocument {
        return reactiveMongoOperations.insert<ChatRoomDocument>(document)
            .awaitSingle()
    }
}