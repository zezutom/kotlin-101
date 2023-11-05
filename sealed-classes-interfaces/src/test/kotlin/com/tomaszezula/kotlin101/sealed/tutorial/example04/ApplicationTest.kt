package com.tomaszezula.kotlin101.sealed.tutorial.example04

import com.tomaszezula.kotlin101.sealed.tutorial.example04.models.User
import com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins.client.configureLogging
import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.ktor.utils.io.*
import kotlin.test.Test
import kotlin.test.assertEquals


class ApplicationTest {

    companion object {
        val TEST_USER = User("user123", "john.doe@gmail.com", "John Doe")
    }

    @Test
    fun `should successfully return the found user`() = testApplication {
        withExternalApiMock(
            content =
            """
                {
                    "id": "${TEST_USER.id}",
                    "email": "${TEST_USER.email}",
                    "name": "${TEST_USER.name}"
                }
            """.trimIndent()
        )
        val response = client.get("/api/v1/users/${TEST_USER.id}")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `should return 404 if the user is not found`() = testApplication {
        withExternalApiMock(status = HttpStatusCode.NotFound)
        val response = client.get("/api/v1/users/${TEST_USER.id}")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Test
    fun `should return 401 if the external API rejects the request as Unauthorized`() = testApplication {
        withExternalApiMock(status = HttpStatusCode.Unauthorized)
        val response = client.get("/api/v1/users/${TEST_USER.id}")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `should return 401 if the external API rejects the request as Forbidden`() = testApplication {
        withExternalApiMock(status = HttpStatusCode.Forbidden)
        val response = client.get("/api/v1/users/${TEST_USER.id}")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `should successfully create a user`() = testApplication {
        withExternalApiMock(
            content = TEST_USER.id
        )
        val response = client.post("/api/v1/users") {
            parameter("email", TEST_USER.email)
            parameter("name", TEST_USER.name)
        }
        assertEquals(HttpStatusCode.Created, response.status)
    }
}

private fun mockEngine(content: String? = null, status: HttpStatusCode = HttpStatusCode.OK) = MockEngine {
    respond(
        content = content?.let { ByteReadChannel(it) } ?: ByteReadChannel.Empty,
        status = status,
        headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
    )
}

private fun ApplicationTestBuilder.withExternalApiMock(
    content: String? = null,
    status: HttpStatusCode = HttpStatusCode.OK
) {
    environment {
        config = HoconApplicationConfig(ConfigFactory.load("application-test.conf"))
    }

    val apiClient = HttpClient(mockEngine(content, status)) {
        configureLogging()
        install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
            json()
        }
    }
    application {
        module(apiClient)
    }
}
