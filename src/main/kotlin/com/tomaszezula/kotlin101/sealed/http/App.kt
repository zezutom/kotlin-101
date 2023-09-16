package com.tomaszezula.kotlin101.sealed.http

import com.tomaszezula.kotlin101.utils.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*

fun main() {
    val httpClient = HttpClient(CIO) {
        configureLogging()
    }
    val apiClient = ApiClient("http://localhost:8080", httpClient)
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/hello") {
                call.toGreetingRequest()?.let { req ->
                    when (val result = apiClient.hello(req)) {
                        is ApiCallResult.Success -> call.success(result.data)
                        is ApiCallResult.Failure -> call.failure(result.reason)
                        else -> call.failure()
                    }
                } ?: call.badRequest()
            }
            post("/register") {
                call.toUserRegistrationRequest()?.let { req ->
                    when (val result = apiClient.registerUser(req)) {
                        is ApiCallResult.Success -> call.success(result.data)
                        is ApiCallResult.Failure -> call.failure(result.reason)
                        is ApiCallResult.BadRequest -> call.badRequest()
                        is ApiCallResult.Unauthorized -> call.unauthorized()
                    }
                } ?: call.badRequest()
            }
        }
    }.start(wait = true)
}
