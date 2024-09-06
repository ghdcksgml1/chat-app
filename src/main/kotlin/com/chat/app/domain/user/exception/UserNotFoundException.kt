package com.chat.app.domain.user.exception

class UserNotFoundException(userId: String) :
    RuntimeException("유저 정보가 존재하지 않습니다. userId = ${userId}") {
}