package com.example.api.presentation.controllers

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.http.MediaType
import java.time.Instant

@ExperimentalCoroutinesApi
class DateTimeControllerTests : CommonControllerTests() {
    @Nested
    inner class GetCurrentDateAndTimeByLocation {
        @Test
        fun `returns 'OK' status and current date and time information`() = runTest {
            val currentTime = Instant.now()

            whenever(mockDateTimeService.getCurrentDateAndTime(AREA_NAME, LOCATION_NAME)).thenReturn(currentTime)

            webTestClient.get()
                .uri("/api/timezone/$AREA_NAME/$LOCATION_NAME")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody(Instant::class.java).isEqualTo(currentTime)

            verify(mockDateTimeService, times(1)).getCurrentDateAndTime(AREA_NAME, LOCATION_NAME)
        }
    }

    companion object DateTimeControllerTestConstants {
        private const val AREA_NAME = "testArea"
        private const val LOCATION_NAME = "testLocation"
    }
}
