package com.chat.app.domain.user

import com.chat.app.domain.user.collection.UserDocument

object UserDocumentFixture {

    fun 일반_사용자(
        id: String = "123456",
        username: String = "일반사용자",
        profilePicture: String? = null,
    ) = UserDocument(id, username, profilePicture)

}