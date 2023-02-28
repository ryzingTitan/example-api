package com.example.api.presentation.controllers

import com.example.api.domain.services.DateTimeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping(path = ["/api/timezone"])
class DateTimeController(private val dateTimeService: DateTimeService) {
    @GetMapping(path = ["/{area}/{location}"])
    suspend fun getCurrentDateAndTimeByLocation(
        @PathVariable(name = "area") area: String,
        @PathVariable(name = "location") location: String,
    ): Instant {
        return dateTimeService.getCurrentDateAndTime(area, location)
    }
}
