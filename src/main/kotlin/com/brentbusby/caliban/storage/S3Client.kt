package com.brentbusby.caliban.storage

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.Bucket
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.brentbusby.caliban.config.AWSConfiguration
import org.apache.commons.io.FileUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class S3Client @Autowired constructor(
    private val awsConfiguration: AWSConfiguration
) {
    val awsCreds = BasicAWSCredentials(awsConfiguration.accessKey, awsConfiguration.secretKey)

    val client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(AWSStaticCredentialsProvider(awsCreds))
        .withRegion(Regions.US_EAST_2)
        .build()

    private val bucketName = awsConfiguration.bucketName

    fun listBuckets(): List<Bucket>{
        return client.listBuckets()
    }

    fun upload(file: File) {
        client.putObject(bucketName, file.name, file)
    }

    fun listContent() {
        val objectListing: ObjectListing = client.listObjects(bucketName)
        for (obj in objectListing.objectSummaries) {
            println(obj.getKey())
        }
    }

    fun getContent(): List<String> {
        var list = mutableListOf<String>()
        var contents = client.listObjects(bucketName)
        for (content in contents.objectSummaries) {
            list.add(content.key)
        }
        return list
    }

    fun download(name: String) {
        val s3Object: S3Object = client.getObject(bucketName, name)
        val inputStream: S3ObjectInputStream = s3Object.objectContent
        FileUtils.copyInputStreamToFile(inputStream, File(name))
    }

    fun getKey(): String {
        return awsConfiguration.accessKey
    }
}