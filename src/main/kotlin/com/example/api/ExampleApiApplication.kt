package com.example.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [ReactiveUserDetailsServiceAutoConfiguration::class])
@ConfigurationPropertiesScan
class ExampleApiApplication

fun main(args: Array<String>) {
    runApplication<ExampleApiApplication>(arrayOf(args).toString())
}
