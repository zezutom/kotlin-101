package com.tomaszezula.kotlin101.coroutines.basics.cancellation

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() = runBlocking {
    // There's no job handle here. It'll automatically cancel after the timeout.
    val result = try {
        withTimeout(3000L) {
            repeat(1000) {
                println("job: I'm sleeping $it ...")
                // Simulate some work
                delay(500L)
            }
            // It times out before reaching this point
            "Done"
        }
    } catch (e: TimeoutCancellationException) {
        // Handle the timeout exception if needed
        println("Caught TimeoutCancellationException: ${e.message}")
        "Operation timed out"
    }

    // If the timeout is reached, a TimeoutCancellationException is thrown
    // and the code below will not execute.
    // Unless you handle it with try-catch.
    println("Result: $result")
}
