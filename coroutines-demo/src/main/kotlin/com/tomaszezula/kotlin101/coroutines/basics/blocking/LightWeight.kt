package com.tomaszezula.kotlin101.coroutines.basics.blocking

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    repeat(50_000) {
        // Launch a lot of coroutines
        launch {
            delay(2000L)
            print(".")
        }
    }
}
