package com.brentbusby.caliban.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties(prefix = "caliban.aws")
class AWSConfiguration {
    lateinit var accessKey: String
    lateinit var secretKey: String
    lateinit var bucketName: String
}