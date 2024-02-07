package com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins.client

import io.ktor.client.*
import io.ktor.client.plugins.logging.*

fun HttpClientConfig<*>.configureLogging() {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.INFO
    }
}