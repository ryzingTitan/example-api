package com.example.api.presentation.controllers

import com.example.api.domain.dtos.User
import com.example.api.domain.exceptions.UserNotFoundException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.never
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType

@ExperimentalCoroutinesApi
class UserControllerTests : CommonControllerTests() {
    @BeforeEach
    fun setup() {
        reset(mockLoggingService, mockUserService)
    }

    @Nested
    inner class GetUsersByFirstName {
        @Test
        fun `returns 'BAD REQUEST' status when the request parameter is missing`() {
            webTestClient.get()
                .uri("/api/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest

            verify(mockUserService, never()).findAllUsersByFirstName(any())
        }

        @Test
        fun `returns 'OK' status with user data that matches the request parameter`() {
            whenever(mockUserService.findAllUsersByFirstName(FIRST_NAME)).thenReturn(flowOf(firstUser, secondUser))

            webTestClient.get()
                .uri("/api/users?firstName=$FIRST_NAME")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(User::class.java).contains(firstUser, secondUser)

            verify(mockUserService, times(1)).findAllUsersByFirstName(FIRST_NAME)
        }
    }

    @Nested
    inner class GetUserById {
        @Test
        fun `returns 'NOT FOUND' status when user doesn't exist`() = runTest {
            whenever(mockUserService.getUserById(firstUser.id)).thenThrow(UserNotFoundException("test exception"))

            webTestClient.get()
                .uri("/api/users/${firstUser.id}")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound

            verify(mockLoggingService, times(1)).info(
                any(),
                eq(firstUser.id),
                eq("Retrieving user data for user id ${firstUser.id}"),
            )
            verify(mockUserService, times(1)).getUserById(firstUser.id)
        }

        @Test
        fun `returns 'OK' status and user information when user exists`() = runTest {
            whenever(mockUserService.getUserById(firstUser.id)).thenReturn(firstUser)

            webTestClient.get()
                .uri("/api/users/${firstUser.id}")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(User::class.java).isEqualTo(firstUser)

            verify(mockLoggingService, times(1)).info(
                any(),
                eq(firstUser.id),
                eq("Retrieving user data for user id ${firstUser.id}"),
            )
            verify(mockUserService, times(1)).getUserById(firstUser.id)
        }
    }

    private val firstUser = User(
        id = 1,
        firstName = FIRST_NAME,
        lastName = LAST_NAME,
        fullName = "$FIRST_NAME $LAST_NAME",
    )

    private val secondUser = User(
        id = 2,
        firstName = FIRST_NAME,
        lastName = LAST_NAME,
        fullName = "$FIRST_NAME $LAST_NAME",
    )

    companion object UserControllerTestConstants {
        private const val FIRST_NAME = "happy"
        private const val LAST_NAME = "carson"
    }
}
