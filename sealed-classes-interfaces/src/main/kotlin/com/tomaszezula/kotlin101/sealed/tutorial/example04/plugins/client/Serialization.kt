package com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins.client

import io.ktor.client.*

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

fun HttpClientConfig<*>.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}