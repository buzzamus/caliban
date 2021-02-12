package com.brentbusby.caliban.utils

import com.brentbusby.caliban.config.AWSConfiguration
import com.brentbusby.caliban.repositories.GameRepository
import com.brentbusby.caliban.storage.S3Client
import org.springframework.beans.factory.annotation.Autowired

class GameCsvWriter @Autowired constructor(
    private val awsConfiguration: AWSConfiguration,
    private val gameRepository: GameRepository
) {

    private fun execute() {
        var s3Client = S3Client(awsConfiguration)
        var allGames = gameRepository.findAll()
    }
}