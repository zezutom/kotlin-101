package com.tomaszezula.kotlin101.sealed.http

import com.tomaszezula.kotlin101.sealed.http.ApiCallResult.Failure
import com.tomaszezula.kotlin101.utils.toApiCallResult
import com.tomaszezula.kotlin101.utils.tryCall
import io.ktor.client.*
import io.ktor.client.request.*

class ApiClient(private val baseUrl: String, private val httpClient: HttpClient) {

    suspend fun hello(request: Greeting.Request): ApiCallResult<Greeting.Response> =
        tryCall(::Failure) {
            httpClient.get("$baseUrl/hello") {
                parameter("from", request.from)
            }.toApiCallResult()
        }

    suspend fun registerUser(request: UserRegistration.Request): ApiCallResult<UserRegistration.Response> =
        tryCall(::Failure) {
            httpClient.post("$baseUrl/register") {
                parameter("email", request.email)
                parameter("firstName", request.firstName)
                parameter("lastName", request.lastName)
                parameter("password", request.password)
            }.toApiCallResult()
        }
}