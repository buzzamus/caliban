package com.brentbusby.caliban

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("com.brentbusby.caliban.config")
class CalibanApplication



fun main(args: Array<String>) {
	runApplication<CalibanApplication>(*args)
}
