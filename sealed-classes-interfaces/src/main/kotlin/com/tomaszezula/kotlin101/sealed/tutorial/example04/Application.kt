package com.tomaszezula.kotlin101.sealed.tutorial.example04

import com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins.client.configureLogging
import com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins.client.configureSerialization
import com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins.configureRouting
import com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins.configureSerialization
import com.tomaszezula.kotlin101.sealed.tutorial.example04.service.UserServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

private val defaultHttpClient = HttpClient(CIO) {
    configureLogging()
    configureSerialization()
}

fun Application.module(httpClient: HttpClient = defaultHttpClient) {
    val userService = UserServiceImpl(httpClient)
    configureRouting(userService)
    configureSerialization()
}