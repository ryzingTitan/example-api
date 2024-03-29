package com.example.api.domain.services

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import com.example.api.domain.configuration.K8sProperties
import com.example.api.domain.configuration.SpringApplicationProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.slf4j.LoggerFactory

class LoggingServiceTests {
    @BeforeEach
    fun setup() {
        whenever(mockSpringApplicationProperties.name).thenReturn("testApp")
        whenever(mockK8sProperties.podName).thenReturn("testPod")
        whenever(mockK8sProperties.nodeName).thenReturn("testNode")

        loggingService = LoggingService(mockK8sProperties, mockSpringApplicationProperties)

        logger = LoggerFactory.getLogger(LoggingService::class.java) as Logger
        appender = ListAppender()
        logger.addAppender(appender)
        appender.start()
    }

    @Nested
    inner class Info {
        @Test
        fun `logs info message`() {
            loggingService.info(logger, USER_ID, "test message")

            assertEquals(1, appender.list.size)
            assertEquals(Level.INFO, appender.list[0].level)
            assertEquals("test message", appender.list[0].message)
            assertEquals(logger.name, appender.list[0].loggerName)
            assertEquals(EXPECTED_MARKERS.toString(), appender.list[0].markerList.toString())
        }
    }

    @Nested
    inner class Error {
        @Test
        fun `logs error message`() {
            val exception = NullPointerException("test exception")

            loggingService.error(logger, USER_ID, "test message", exception)

            assertEquals(1, appender.list.size)
            assertEquals(Level.ERROR, appender.list[0].level)
            assertEquals("test message", appender.list[0].message)
            assertEquals(logger.name, appender.list[0].loggerName)
            assertEquals(EXPECTED_MARKERS.toString(), appender.list[0].markerList.toString())
            assertEquals(exception.message, appender.list[0].throwableProxy.message)
            assertEquals(exception.javaClass.name, appender.list[0].throwableProxy.className)
        }
    }

    private lateinit var loggingService: LoggingService
    private lateinit var logger: Logger
    private lateinit var appender: ListAppender<ILoggingEvent>

    private val mockK8sProperties = mock<K8sProperties>()
    private val mockSpringApplicationProperties = mock<SpringApplicationProperties>()

    companion object LoggingServiceTestConstants {
        private const val USER_ID = 1
        private val EXPECTED_MARKERS = listOf(
            "userId=1",
            "applicationName=testApp",
            "podName=testPod",
            "nodeName=testNode",
        )
    }
}
