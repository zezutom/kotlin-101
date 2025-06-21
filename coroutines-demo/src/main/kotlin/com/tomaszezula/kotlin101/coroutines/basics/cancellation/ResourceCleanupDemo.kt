package com.tomaszezula.kotlin101.coroutines.basics.cancellation

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("Cleaning up resources...")

            // This block will run when the job is cancelled
            // It needs a special context, otherwise it would throw a CancellationException
            withContext(NonCancellable) {
                println("job: I'm running a non-cancellable block to clean up resources.")
                delay(1000L) // Simulate some resource cleanup
                println("job: Resource cleanup completed after a delay.")
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}
