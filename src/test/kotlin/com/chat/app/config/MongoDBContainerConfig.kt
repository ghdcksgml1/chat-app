package com.chat.app.config

import com.chat.app.domain.message.collection.ChatMessageDocument
import com.chat.app.domain.room.collection.ChatRoomDocument
import com.chat.app.domain.user.collection.UserDocument
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import org.springframework.data.mongodb.core.collectionExists
import org.springframework.data.mongodb.core.dropCollection
import org.springframework.data.mongodb.core.remove
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Testcontainers

const val MONGODB_IMAGE = "mongo:5.0"

@SpringBootTest
@Testcontainers
class MongoDBContainerConfig {

    companion object {
        @JvmStatic
        private val genericContainer = MongoDBContainer(MONGODB_IMAGE).withReuse(true)

        @JvmStatic
        @DynamicPropertySource
        private fun propertiesConfig(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.mongodb.uri") { "mongodb://localhost:${genericContainer.firstMappedPort}/chat?authSource=admin" }
        }

        init {
            genericContainer.start()
        }
    }

    @Autowired lateinit var reactiveMongoOperations: ReactiveMongoOperations

    @AfterEach
    fun tearDown() {
        runBlocking {
            reactiveMongoOperations.remove<UserDocument>()
            reactiveMongoOperations.remove<ChatRoomDocument>()
            reactiveMongoOperations.remove<ChatMessageDocument>()
        }
    }
}