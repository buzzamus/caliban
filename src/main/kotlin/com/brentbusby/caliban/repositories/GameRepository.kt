package com.brentbusby.caliban.repositories

import com.brentbusby.caliban.entities.Game
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface GameRepository : CrudRepository<Game, Long> {
    fun findByTitle(title: String): Game?

    @Query(value = "select count(id) as gameCount, studio as studio from Game group by studio", nativeQuery = true)
    fun aggregateReleasesByStudio(): List<tempAggRelease>

    interface tempAggRelease {
        val gameCount: Int
        val studio: String
    }
}