package com.example.api.domain.configuration

import lombok.Generated
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@Generated
@ConstructorBinding
@ConfigurationProperties(prefix = "k8s")
data class K8sProperties(
    val podName: String,
    val nodeName: String
)