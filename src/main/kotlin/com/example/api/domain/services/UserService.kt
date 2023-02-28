package com.example.api.domain.services

import com.example.api.data.user.entities.UserEntity
import com.example.api.data.user.repositories.UserRepository
import com.example.api.domain.dtos.User
import com.example.api.domain.exceptions.UserNotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import kotlin.jvm.Throws

@Service
class UserService(private val userRepository: UserRepository) {
    fun findAllUsersByFirstName(firstName: String): Flow<User> {
        return userRepository
            .findAllByFirstName(firstName)
            .map { userEntity ->
                map(userEntity)
            }
    }

    @Throws(UserNotFoundException::class)
    suspend fun getUserById(userId: Int): User {
        val userEntity =
            userRepository.findById(userId) ?: throw UserNotFoundException("User with id $userId does not exist")

        return map(userEntity)
    }

    private fun map(userEntity: UserEntity): User {
        return User(
            id = userEntity.id ?: 0,
            firstName = userEntity.firstName,
            lastName = userEntity.lastName,
            fullName = "${userEntity.firstName} ${userEntity.lastName}",
        )
    }
}
