package com.example.api.data.datetime.repositories

import com.example.api.data.datetime.entities.DateTimeEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Repository
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Repository
class DateTimeRepository(
    @Qualifier("External Date Time Server")
    private val externalDateTimeServer: WebClient
) {
    suspend fun getCurrentDateAndTime(area: String, location: String): DateTimeEntity {
        return externalDateTimeServer
            .get()
            .uri("/timezone/$area/$location")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .awaitBody()
    }
}
