package com.brentbusby.caliban.utils

import com.brentbusby.caliban.config.AWSConfiguration
import com.brentbusby.caliban.entities.Game
import com.brentbusby.caliban.entities.Genre
import com.brentbusby.caliban.repositories.GameRepository
import com.brentbusby.caliban.storage.S3Client
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDate

@DataJpaTest
@EnableJpaRepositories(basePackages = ["com.brentbusby.caliban"])
@EntityScan(basePackages = ["com.brentbusby.caliban.entities"])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = [AWSConfiguration::class])
class GameAggregatorCsvWriterTest @Autowired constructor(
    private val awsConfiguration: AWSConfiguration,
    private val entityManager: TestEntityManager,
    private val gameRepository: GameRepository
) {

    @Test
    fun `it writes the aggregated games and uploads a file to s3`() {
        val s3Client = S3Client(awsConfiguration)
        val aggGameAggregatorCsvWriter = GameAggregatorCsvWriter(awsConfiguration, gameRepository)

        val game = Game("Halo", Genre.shooter, "Bungie", 2001)
        val game2 = Game("Halo 2", Genre.shooter, "Bungie", 2004)
        val game3 = Game("Halo 3", Genre.shooter, "Bungie", 2007)
        entityManager.persist(game)
        entityManager.persist(game2)
        entityManager.persist(game3)
        entityManager.flush()

        aggGameAggregatorCsvWriter.execute()

        val target = s3Client.getContent()

        Assertions.assertTrue(target.last().startsWith("temp-agg-file-${LocalDate.now()}"))
    }
}