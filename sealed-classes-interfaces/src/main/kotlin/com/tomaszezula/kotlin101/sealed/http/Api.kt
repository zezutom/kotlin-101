package com.tomaszezula.kotlin101.sealed.http

object Greeting {
    data class Request(val from: String)
    data class Response(val greeting: String)
}

object UserRegistration {
    data class Request(
        val email: String,
        val firstName: String,
        val lastName: String,
        val password: String
    )

    data class Response(val userId: String)
}