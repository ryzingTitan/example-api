package com.example.api.cucumber.common

import com.example.api.cucumber.stubs.ExternalDateTimeServer
import io.cucumber.java.After
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.Instant
import java.time.temporal.ChronoUnit

class DateTimeStepDefs(private val externalDateTimeServer: ExternalDateTimeServer) {
    @Given("the current date and time in the area {string} and the location {string}")
    fun theCurrentDateAndTimeInTheAreaAndTheLocation(area: String, location: String) {
        externalDateTimeServer.startServer(currentInstant, area, location)
    }

    @Then("the current date and time is correct")
    fun theCurrentDateAndTimeIsCorrect() {
        assertEquals(currentInstant.truncatedTo(ChronoUnit.SECONDS), currentDateTime)
    }

    @After
    fun teardown() {
        externalDateTimeServer.stopServer()
    }

    private val currentInstant = Instant.now()

    companion object DateTimeStepDefsSharedState {
        internal var currentDateTime: Instant? = null
    }
}
