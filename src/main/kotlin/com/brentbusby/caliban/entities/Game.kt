package com.brentbusby.caliban.entities

import java.time.Year
import javax.persistence.*

enum class Genre {
    action,
    adventure,
    battle_royale,
    fighting,
    horror,
    music,
    platform,
    racing,
    rpg,
    shooter,
    simulation,
    sports,
    strategy,
    survival,
}

@Entity
class Game(
    var title: String,
    @Enumerated(EnumType.STRING) var genre: Genre,
    var studio: String,
    var yearReleased: Int,
    @Id @GeneratedValue var id: Long? = null
)