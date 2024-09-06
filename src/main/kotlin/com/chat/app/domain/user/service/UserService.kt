package com.chat.app.domain.user.service

import com.chat.app.domain.user.collection.UserDocument
import com.chat.app.domain.user.dto.CreateUserCommand
import com.chat.app.domain.user.dto.ExposureUserInfo
import com.chat.app.domain.user.exception.UserNotFoundException
import com.chat.app.domain.user.jwt.JwtUserDecoder
import com.chat.app.domain.user.mapper.UserMapper
import com.chat.app.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Random

@Service
class UserService(
    private val jwtUserDecoder: JwtUserDecoder,
    private val userRepository: UserRepository,
) {
    suspend fun getUserInfo(token: String): ExposureUserInfo {
        val userId = jwtUserDecoder.getUserId(token)
        val user = userRepository.findById(userId)
            ?:let {
                val document = UserDocument(
                    id = userId,
                    username = "랜덤 사용자 ${Random().nextInt(Int.MAX_VALUE)}"
                )
                userRepository.save(document)
            }

        return UserMapper.mapToDomain(user)
    }

    suspend fun findById(userId: String): ExposureUserInfo {
        return userRepository.findById(userId)
            ?.let { UserMapper.mapToDomain(it) }
            ?: throw UserNotFoundException(userId)
    }

    suspend fun findAllUserInfoIn(userIds: List<String>): List<ExposureUserInfo> {
        return userRepository.findAllByIdsIn(userIds)
            .map { UserMapper.mapToDomain(it) }
    }

    suspend fun saveUser(command: CreateUserCommand): ExposureUserInfo =
        with(command) {
            UserDocument(
                id = id,
                username = username,
                profilePicture = profilePicture,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        }.let {
            userRepository.save(it)
        }.let {
            UserMapper.mapToDomain(it)
        }
}