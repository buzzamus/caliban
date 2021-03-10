package com.brentbusby.caliban.repositories

import com.brentbusby.caliban.entities.Game
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface GameRepository : JpaRepository<Game, Long> {
    fun findByTitle(title: String): Game?

    @Query(value = "select count(id) as gameCount, studio as studio from Game group by studio", nativeQuery = true)
    fun aggregateReleasesByStudio(): List<tempAggRelease>

    interface tempAggRelease {
        val gameCount: Int
        val studio: String
    }

    fun findByStudio(studio: String, pageable: Pageable): Page<Game>
}