package ru.kaiten

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KaitenApplication

fun main(args: Array<String>) {
    runApplication<KaitenApplication>(*args)
}

