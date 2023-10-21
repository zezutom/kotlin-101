package com.tomaszezula.kotlin101.sealed.tutorial

sealed interface Error {
    val message: String
}

sealed class HttpError(override val message: String) : Error {
    private constructor(statusCode: Int) : this("HTTP error: $statusCode")

    object InternalServerError : HttpError(500)
    companion object {
        fun fromStatusCode(statusCode: Int): HttpError {
            return when (statusCode) {
                400 -> BadRequest("Bad request")
                404 -> NotFound
                else -> InternalServerError
            }
        }
    }
}

class BadRequest(message: String) : HttpError(message)
object NotFound : HttpError("Not found")

fun main() {
//    val error = HttpError("Bad request")  // Sealed classes cannot be instantiated
    val error = HttpError.fromStatusCode(404)
    println(error.message)
}