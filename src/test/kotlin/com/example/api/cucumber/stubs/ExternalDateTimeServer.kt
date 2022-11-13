package com.example.api.cucumber.stubs

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class ExternalDateTimeServer {
    private val wireMockServer = WireMockServer(WireMockConfiguration.wireMockConfig().port(8090))

    fun startServer(currentInstant: Instant, area: String, location: String) {
        wireMockServer.start()
        setupExternalDateTimeEndpoint(currentInstant, area, location)
    }

    private fun setupExternalDateTimeEndpoint(currentInstant: Instant, area: String, location: String) {
        wireMockServer.stubFor(
            get(urlEqualTo("/api/timezone/$area/$location"))
                .willReturn(
                    aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(responseBody.replace("<currentInstant>", currentInstant.epochSecond.toString()))
                )
        )
    }

    fun stopServer() {
        wireMockServer.stop()
    }

    private val responseBody = """
        {
          "abbreviation": "EDT",
          "client_ip": "162.245.158.239",
          "datetime": "2022-10-12T16:10:30.863638-04:00",
          "day_of_week": 3,
          "day_of_year": 285,
          "dst": true,
          "dst_from": "2022-03-13T07:00:00+00:00",
          "dst_offset": 3600,
          "dst_until": "2022-11-06T06:00:00+00:00",
          "raw_offset": -18000,
          "timezone": "America/Indiana",
          "unixtime": <currentInstant>,
          "utc_datetime": "2022-10-12T20:10:30.863638+00:00",
          "utc_offset": "-4:00",
          "week_number": 41
        }
    """
}
