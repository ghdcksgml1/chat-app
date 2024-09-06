package com.chat.app.domain.message.repository

import com.chat.app.domain.message.collection.ChatMessageDocument
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.data.mongodb.core.query.lt
import org.springframework.stereotype.Repository

@Repository
class CustomChatMessageRepositoryImpl(
    private val reactiveMongoOperations: ReactiveMongoOperations
) : CustomChatMessageRepository {

    override suspend fun getLatestChatMessage(chatRoomId: String, count: Int, cursorId: String?): List<ChatMessageDocument> {
        val query = Query()
            .addCriteria(cursorId?.let { ChatMessageDocument::id lt cursorId } ?: Criteria())
            .addCriteria(ChatMessageDocument::chatRoomId isEqualTo chatRoomId)
            .with(Sort.by(Sort.Direction.DESC, ChatMessageDocument::id.name))
            .limit(count)

        return reactiveMongoOperations.find<ChatMessageDocument>(query)
            .collectList()
            .awaitSingle()
            .sortedBy { it.id }
    }

    override suspend fun createChatMessage(document: ChatMessageDocument): ChatMessageDocument {
        return reactiveMongoOperations.insert<ChatMessageDocument>(document)
            .awaitSingle()
    }
}