package com.brentbusby.caliban

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CalibanApplication

fun main(args: Array<String>) {
	runApplication<CalibanApplication>(*args)
}
