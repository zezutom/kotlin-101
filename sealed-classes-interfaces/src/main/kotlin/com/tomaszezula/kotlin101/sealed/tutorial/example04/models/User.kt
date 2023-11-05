package com.tomaszezula.kotlin101.sealed.tutorial.example04.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val id: UserId, val email: String, val name: String)

typealias UserId = String
