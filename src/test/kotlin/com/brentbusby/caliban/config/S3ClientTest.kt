package com.brentbusby.caliban.config

import com.brentbusby.caliban.storage.S3Client
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(classes = [AWSConfiguration::class])
class S3ClientTest @Autowired constructor(
        val awsConfiguration: AWSConfiguration
) {

    @Test
    fun `it lists all buckets`() {
        println("********************************")
        println(awsConfiguration.bucketName)
       // println(x.listBuckets())
    }
}