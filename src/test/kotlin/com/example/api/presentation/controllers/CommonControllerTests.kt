package com.example.api.presentation.controllers

import com.example.api.domain.services.DateTimeService
import com.example.api.domain.services.LoggingService
import com.example.api.domain.services.UserService
import com.example.api.presentation.configuration.SecurityConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
@Import(value = [SecurityConfiguration::class])
@ActiveProfiles("test")
@Suppress("UnnecessaryAbstractClass")
abstract class CommonControllerTests {
    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @MockBean
    protected lateinit var mockLoggingService: LoggingService

    @MockBean
    protected lateinit var mockUserService: UserService

    @MockBean
    protected lateinit var mockDateTimeService: DateTimeService
}
