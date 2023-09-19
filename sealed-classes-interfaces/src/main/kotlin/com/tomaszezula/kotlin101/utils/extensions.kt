package com.tomaszezula.kotlin101.utils

import com.tomaszezula.kotlin101.sealed.http.ApiCallResult
import com.tomaszezula.kotlin101.sealed.http.Greeting
import com.tomaszezula.kotlin101.sealed.http.UserRegistration
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.CancellationException

fun <T> tryRun(onError: (String) -> T, block: () -> T): T {
    return try {
        block()
    } catch (t: Throwable) {
        onError(t.message ?: "Unknown error")
    }
}

suspend fun <T> tryCall(onError: (String) -> T, block: suspend () -> T): T {
    return try {
        block()
    } catch (e: CancellationException) {
        throw e
    } catch (t: Throwable) {
        onError(t.message ?: "Unknown error")
    }
}

fun HttpClientConfig<*>.configureLogging() {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.INFO
    }
}

suspend inline fun <reified T> HttpResponse.toApiCallResult(): ApiCallResult<T> =
    when (this.status.value) {
        200 -> ApiCallResult.Success(this.body())
        400 -> ApiCallResult.BadRequest
        401 -> ApiCallResult.Unauthorized
        404 -> ApiCallResult.NotFound
        else -> ApiCallResult.Failure("Unknown error")
    }

fun ApplicationCall.toGreetingRequest(): Greeting.Request? =
    this.request.queryParameters["from"]?.let { Greeting.Request(it) }

fun ApplicationCall.toUserRegistrationRequest(): UserRegistration.Request? =
    this.parameters["email"]?.let { email ->
        this.parameters["firstName"]?.let { firstName ->
            this.parameters["lastName"]?.let { lastName ->
                this.parameters["password"]?.let { password ->
                    UserRegistration.Request(email, firstName, lastName, password)
                }
            }
        }
    }

suspend fun ApplicationCall.success(data: Any? = null) =
    data?.let { this.respond(HttpStatusCode.OK, it) } ?: this.respond(HttpStatusCode.OK)

suspend fun ApplicationCall.failure(reason: String = "Unexpected error") =
    this.respond(HttpStatusCode.InternalServerError, reason)

suspend fun ApplicationCall.badRequest() =
    this.respond(HttpStatusCode.BadRequest)

suspend fun ApplicationCall.unauthorized() =
    this.respond(HttpStatusCode.Unauthorized)
