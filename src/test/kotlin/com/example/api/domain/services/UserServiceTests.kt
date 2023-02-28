package com.example.api.domain.services

import com.example.api.data.user.entities.UserEntity
import com.example.api.data.user.repositories.UserRepository
import com.example.api.domain.dtos.User
import com.example.api.domain.exceptions.UserNotFoundException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class UserServiceTests {
    @BeforeEach
    fun setup() {
        userService = UserService(mockUserRepository)
        reset(mockUserRepository)
    }

    @Nested
    inner class FindAllUsersByFirstName {
        @Test
        fun `returns all users with the first name that is provided`() = runTest {
            whenever(mockUserRepository.findAllByFirstName(userEntity.firstName)).thenReturn(flowOf(userEntity))

            val users = userService.findAllUsersByFirstName(userEntity.firstName)

            assertEquals(listOf(user), users.toList())

            verify(mockUserRepository, times(1)).findAllByFirstName(userEntity.firstName)
        }
    }

    @Nested
    inner class GetUserById {
        @Test
        fun `returns the user if the user id exists`() = runTest {
            whenever(mockUserRepository.findById(userEntity.id!!)).thenReturn(userEntity)

            val returnedUser = userService.getUserById(userEntity.id!!)

            assertEquals(user, returnedUser)

            verify(mockUserRepository, times(1)).findById(userEntity.id!!)
        }

        @Test
        fun `throws UserNotFoundException if the user id doesn't exist`() = runTest {
            whenever(mockUserRepository.findById(userEntity.id!!)).thenReturn(null)

            val exception = assertThrows<UserNotFoundException> { userService.getUserById(userEntity.id!!) }

            assertEquals("User with id ${userEntity.id} does not exist", exception.message)

            verify(mockUserRepository, times(1)).findById(userEntity.id!!)
        }
    }

    private lateinit var userService: UserService

    private val mockUserRepository = mock<UserRepository>()

    private val userEntity = UserEntity(
        id = 1,
        username = "testUser",
        firstName = "test",
        lastName = "user",
    )

    private val user = User(
        id = 1,
        firstName = "test",
        lastName = "user",
        fullName = "test user",
    )
}
