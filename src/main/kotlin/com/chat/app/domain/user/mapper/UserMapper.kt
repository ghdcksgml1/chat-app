package com.chat.app.domain.user.mapper

import com.chat.app.domain.user.collection.UserDocument
import com.chat.app.domain.user.dto.ExposureUserInfo

object UserMapper {
    fun mapToDomain(document: UserDocument): ExposureUserInfo =
        with(document) {
            ExposureUserInfo(
                id = id,
                username = username,
                profilePicture = profilePicture
            )
        }
}