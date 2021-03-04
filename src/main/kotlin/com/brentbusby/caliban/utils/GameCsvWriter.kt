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
class GameCsvWriter @Autowired constructor(
    private val awsConfiguration: AWSConfiguration,
    private val gameRepository: GameRepository
) {

    companion object {
        private val headers = mapOf<String, String>(
            "TITLE" to "title",
            "STUDIO" to "studio",
            "GENRE" to "genre",
            "YEAR" to "year released"
        )
    }

    fun execute() {
        val s3Client = S3Client(awsConfiguration)
        val allGames = gameRepository.findAll()
        
        val tempFile = File.createTempFile("temp-file-${LocalDate.now()}", ".csv")
        tempFile.deleteOnExit()

        csvWriter().open(tempFile) {
            writeRow(
                GameCsvWriter.headers["TITLE"],
                GameCsvWriter.headers["STUDIO"],
                GameCsvWriter.headers["GENRE"],
                GameCsvWriter.headers["YEAR"]
            )
            allGames.forEach {
                writeRow(
                    it.title,
                    it.studio,
                    it.genre,
                    it.yearReleased
                )
            }
        }
        s3Client.upload(tempFile)
    }
}