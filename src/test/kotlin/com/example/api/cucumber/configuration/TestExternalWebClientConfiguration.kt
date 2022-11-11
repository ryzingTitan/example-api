package com.example.api.cucumber.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@Profile("test")
class TestExternalWebClientConfiguration {
    @Bean(name = ["External Date Time Server"])
    fun externalDateTimeWebClient() = WebClient.create("http://localhost:8090/api")
}
