package com.brentbusby.caliban.repositories

import com.brentbusby.caliban.entities.Game
import com.brentbusby.caliban.entities.Genre
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val gameRepository: GameRepository
) {

    @Test
    fun `it saves and then finds the game by title`() {
        val game = Game("Gears of War 5", Genre.shooter, "the Coalition", 2019)
        entityManager.persist(game)
        entityManager.flush()
        val found = gameRepository.findByTitle("Gears of War 5")
        Assertions.assertEquals(found, game)
    }

    @Test
    fun `it aggregates the releases by studio`() {
        val game = Game("Halo", Genre.shooter, "Bungie", 2001)
        val game2 = Game("Halo 2", Genre.shooter, "Bungie", 2004)
        val game3 = Game("Halo 3", Genre.shooter, "Bungie", 2007)
        entityManager.persist(game)
        entityManager.persist(game2)
        entityManager.persist(game3)
        entityManager.flush()
        val results = gameRepository.aggregateReleasesByStudio()
        Assertions.assertEquals(3, results[0].gameCount)
    }
}