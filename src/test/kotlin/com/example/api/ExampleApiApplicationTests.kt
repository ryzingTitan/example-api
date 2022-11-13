package com.example.api

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class ExampleApiApplicationTests {
    @Nested
    inner class Context {
        @Test
        fun `loads successfully`() {
            // Context test
        }
    }
}
