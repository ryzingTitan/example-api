package com.example.api.presentation.controllers

import com.example.api.domain.dtos.User
import com.example.api.domain.services.LoggingService
import com.example.api.domain.services.UserService
import kotlinx.coroutines.flow.Flow
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/users"])
class UserController(
    private val loggingService: LoggingService,
    private val userService: UserService,
) {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @GetMapping
    fun getUsersByFirstName(@RequestParam(name = "firstName") firstName: String): Flow<User> {
        return userService.findAllUsersByFirstName(firstName)
    }

    @GetMapping(path = ["/{id}"])
    suspend fun getUserById(@PathVariable(name = "id") userId: Int): User {
        this.loggingService.info(
            logger,
            userId,
            "Retrieving user data for user id $userId",
        )

        return userService.getUserById(userId)
    }
}
