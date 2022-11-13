package com.example.api.data.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@Profile("!test")
class ExternalWebClientConfiguration {
    @Bean(name = ["External Date Time Server"])
    fun externalDateTimeServerWebClient() = WebClient.create("http://worldtimeapi.org/api")
}
