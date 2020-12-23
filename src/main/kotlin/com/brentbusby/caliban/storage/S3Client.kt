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

    val s3Client = AmazonS3ClientBuilder
        .standard()
        .withCredentials(AWSStaticCredentialsProvider(awsCreds))
        .withRegion(Regions.US_EAST_2)
        .build()

    private val bucketName = awsConfiguration.bucketName

    fun listBuckets(){
        val buckets: List<Bucket> = s3Client.listBuckets()
        for (bucket in buckets) {
            println(bucket.name)
        }
    }

    fun upload() {
        val file = "input.csv"
        val filePath = File("Input.csv")
        s3Client.putObject(bucketName, file, filePath)
    }

    fun listContent() {
        val objectListing: ObjectListing = s3Client.listObjects(bucketName)
        for (thingy in objectListing.objectSummaries) {
            println(thingy.getKey())
        }
    }

    fun download(name: String) {
        val s3Object: S3Object = s3Client.getObject(bucketName, name)
        val inputStream: S3ObjectInputStream = s3Object.objectContent
        FileUtils.copyInputStreamToFile(inputStream, File(name))
    }

    fun getKey(): String {
        return awsConfiguration.accessKey
    }
}