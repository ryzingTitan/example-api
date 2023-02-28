package com.example.api.cucumber.controllers

import com.example.api.cucumber.common.CommonControllerStepDefs.CommonControllerStepDefsSharedState.responseStatus
import com.example.api.cucumber.common.DateTimeStepDefs.DateTimeStepDefsSharedState.currentDateTime
import io.cucumber.java.Before
import io.cucumber.java.en.When
import kotlinx.coroutines.runBlocking
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitEntity
import org.springframework.web.reactive.function.client.awaitExchange
import java.time.Instant

class DateTimeControllerStepDefs {
    @Before("@currentDateTime")
    fun setup() {
        webClient = WebClient.create("http://localhost:$port/api/timezone/")
    }

    @When("a request is made to retrieve the current date and time for the area {string} and the location {string}")
    fun aRequestIsMadeToRetrieveTheCurrentDateAndTimeForTheAreaAndTheLocation(area: String, location: String) {
        runBlocking {
            webClient.get()
                .uri("/$area/$location")
                .accept(MediaType.APPLICATION_JSON)
                .awaitExchange { clientResponse ->
                    handleResponse(clientResponse)
                }
        }
    }

    private suspend fun handleResponse(clientResponse: ClientResponse) {
        responseStatus = clientResponse.statusCode() as HttpStatus

        if (clientResponse.statusCode() == HttpStatus.OK) {
            currentDateTime = clientResponse.awaitEntity<Instant>().body
        }
    }

    @LocalServerPort
    private val port = 0

    private lateinit var webClient: WebClient
}
