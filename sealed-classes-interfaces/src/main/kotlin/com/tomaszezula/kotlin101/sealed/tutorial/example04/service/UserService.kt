package com.tomaszezula.kotlin101.sealed.tutorial.example04.service

import com.tomaszezula.kotlin101.sealed.tutorial.example04.models.User
import com.tomaszezula.kotlin101.sealed.tutorial.example04.models.UserId
import com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins.resultOf
import io.ktor.client.*
import io.ktor.client.request.*

interface UserService {
    suspend fun getUser(userId: UserId): ServiceResult<User>
    suspend fun registerUser(email: String, name: String): ServiceResult<UserId>
}

class UserServiceImpl(private val httpClient: HttpClient) : UserService {
    companion object {
        const val EXTERNAL_API_URL = "https://api.example.com"
    }

    override suspend fun getUser(userId: UserId): ServiceResult<User> = resultOf {
        httpClient.get("$EXTERNAL_API_URL/v1/users/$userId")
    }

    override suspend fun registerUser(email: String, name: String): ServiceResult<UserId> = resultOf {
        httpClient.post("$EXTERNAL_API_URL/v1/users") {
            parameter("email", email)
            parameter("name", name)
        }
    }
}

