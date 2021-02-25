package com.brentbusby.caliban.utils

import com.brentbusby.caliban.config.AWSConfiguration
import com.brentbusby.caliban.repositories.GameRepository
import com.brentbusby.caliban.storage.S3Client
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDate

@Service
class GameAggregatorCsvWriter @Autowired constructor(
    private val awsConfiguration: AWSConfiguration,
    private val gameRepository: GameRepository
) {
    companion object {
        private val headers = mapOf<String, String>(
            "STUDIO" to "studio",
            "COUNT" to "number of titles"
        )
    }

    fun execute() {
        val s3Client = S3Client(awsConfiguration)
        var aggregatedGames = gameRepository.aggregateReleasesByStudio()

        val tempFile = File.createTempFile("temp-agg-file-${LocalDate.now()}", ".csv")
        tempFile.deleteOnExit()

        csvWriter().open(tempFile) {
            writeRow(
                GameAggregatorCsvWriter.headers["STUDIO"],
                GameAggregatorCsvWriter.headers["COUNT"]
            )
            aggregatedGames.forEach {
                writeRow(
                    it.studio,
                    it.gameCount
                )
            }
        }
        s3Client.upload(tempFile)
    }
}