package com.example.api.domain.services

import com.example.api.domain.configuration.K8sProperties
import com.example.api.domain.configuration.SpringApplicationProperties
import net.logstash.logback.marker.LogstashMarker
import net.logstash.logback.marker.Markers
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
class LoggingService(
    private val k8sProperties: K8sProperties,
    private val springApplicationProperties: SpringApplicationProperties,
) {
    fun info(logger: Logger, userId: Int, message: String) {
        logger.info(
            Markers.append("userId", userId)
                .and<LogstashMarker>(Markers.append("applicationName", springApplicationProperties.name))
                .and<LogstashMarker>(Markers.append("podName", k8sProperties.podName))
                .and(Markers.append("nodeName", k8sProperties.nodeName)),
            message,
        )
    }

    fun error(logger: Logger, userId: Int, message: String, exception: Exception) {
        logger.error(
            Markers.append("userId", userId)
                .and<LogstashMarker>(Markers.append("applicationName", springApplicationProperties.name))
                .and<LogstashMarker>(Markers.append("podName", k8sProperties.podName))
                .and(Markers.append("nodeName", k8sProperties.nodeName)),
            message,
            exception,
        )
    }
}
