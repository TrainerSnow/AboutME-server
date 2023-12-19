package com.snow.aboutme

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AboutMeServerApplication

fun main(args: Array<String>) {
    runApplication<AboutMeServerApplication>(*args)
}
