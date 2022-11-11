package com.example.api.domain.services

import com.example.api.data.datetime.repositories.DateTimeRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DateTimeService(private val dateTimeRepository: DateTimeRepository) {
    suspend fun getCurrentDateAndTime(area: String, location: String): Instant {
        val currentDateTime = dateTimeRepository.getCurrentDateAndTime(area, location)
        return Instant.ofEpochSecond(currentDateTime.unixTime)
    }
}
