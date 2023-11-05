package com.tomaszezula.kotlin101.sealed.tutorial.example02

sealed interface Vehicle {
    val registrationNumber: String
}

data class Car(override val registrationNumber: String, val numberOfSeats: Int) : Vehicle
data class Truck(override val registrationNumber: String, val capacity: Int) : Vehicle
