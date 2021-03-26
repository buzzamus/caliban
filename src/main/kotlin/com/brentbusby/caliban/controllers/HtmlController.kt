package com.brentbusby.caliban.controllers

import com.brentbusby.caliban.entities.Game
import com.brentbusby.caliban.entities.Genre
import com.brentbusby.caliban.repositories.GameRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class HtmlController @Autowired constructor(val gameRepository: GameRepository) {

    @GetMapping("/")
    fun home(model: Model): String {
        model["title"] = "Home"
        return "home"
    }

    @GetMapping("/gameForm")
    fun gameForm(model: Model): String {
        model["title"] = "gameForm"
        return "gameForm"
    }

    @PostMapping("/gameForm")
    fun gamePost(
        @RequestParam("title") title: String,
        @RequestParam("genre") genre: String,
        @RequestParam("studio") studio: String,
        @RequestParam("yearReleased") yearReleased: String,
        redirect: RedirectAttributes
    ): String {
        val newGame = Game(
            title,
            Genre.valueOf(genre),
            studio,
            yearReleased.toInt()
        )
        gameRepository.save(newGame)
        val gameFromDB = gameRepository.findByTitle(title)
        redirect.addFlashAttribute("game", gameFromDB)
        return "redirect:results"
    }

    @GetMapping("/results")
    fun results(): String {
        return "results"
    }

}