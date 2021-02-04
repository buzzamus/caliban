package com.brentbusby.caliban.config

import org.hibernate.cfg.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties
class AWSConfiguration {
    @Value("\${caliban.aws.accessKey}")
    lateinit var accessKey: String

    @Value("\${caliban.aws.secretKey}")
    lateinit var secretKey: String

    @Value("\${caliban.aws.bucketName}")
    lateinit var bucketName: String

}