package com.brentbusby.caliban.storage

import org.junit.jupiter.api.Test

class S3ClientTest {

    @Test
    fun `it lists all buckets`() {
        val x = S3Client()
        println("********************************")
        println(x.listBuckets())
    }
}