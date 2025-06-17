package com.tomaszezula.kotlin101.coroutines.basics.blocking

import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun main() = runBlocking {
    // Waits for 2s and then prints all at once! (concurrent execution)
    coPrintDots()

    // Runs out of memory
    printDots()
}

private suspend fun coPrintDots() = coroutineScope {
    repeat(50_000) {
        // Launch a lot of coroutines
        launch {
            delay(2000L)
            print(".")
        }
    }
}
private fun printDots() {
    repeat(50_000) {
        // Launch a lot of coroutines
        thread {
            Thread.sleep(2000)
            print(".")
        }
    }
}

