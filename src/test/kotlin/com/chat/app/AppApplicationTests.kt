package com.chat.app

import com.chat.app.config.MongoDBContainerConfig
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

class AppApplicationTests : MongoDBContainerConfig() {

	@Test
	fun contextLoads() {
	}

}
