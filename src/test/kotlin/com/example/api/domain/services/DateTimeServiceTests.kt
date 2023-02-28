package com.example.api.domain.services

import com.example.api.data.datetime.entities.DateTimeEntity
import com.example.api.data.datetime.repositories.DateTimeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.Instant
import java.time.temporal.ChronoUnit

@ExperimentalCoroutinesApi
class DateTimeServiceTests {
    @BeforeEach
    fun setup() {
        dateTimeService = DateTimeService(mockDateTimeRepository)
    }

    @Nested
    inner class GetCurrentDateAndTime {
        @Test
        fun `returns current date and time`() = runTest {
            whenever(mockDateTimeRepository.getCurrentDateAndTime(AREA_NAME, LOCATION_NAME)).thenReturn(currentDateTime)

            val dateTime = dateTimeService.getCurrentDateAndTime(AREA_NAME, LOCATION_NAME)

            assertEquals(expectedDateTime, dateTime)
        }
    }

    private lateinit var dateTimeService: DateTimeService

    private val mockDateTimeRepository = mock<DateTimeRepository>()
    private val currentInstant = Instant.now()
    private val currentDateTime = DateTimeEntity(
        "",
        "",
        "",
        0,
        0,
        false,
        "",
        1,
        "",
        1,
        "",
        currentInstant.epochSecond,
        "",
        "",
        1,
    )
    private val expectedDateTime = currentInstant.truncatedTo(ChronoUnit.SECONDS)

    companion object DateTimeServiceTestConstants {
        private const val AREA_NAME = "testArea"
        private const val LOCATION_NAME = "testLocation"
    }
}
