package com.tomaszezula.kotlin101.coroutines.basics.blocking

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    launch {    // Launch a new coroutine and continue
        doWorld()

        val job = launch {
            delay(1000L)
            println("Job done!")
        }
        job.join()  // Wait until the child coroutine completes
        println("Done")
    }
    println("Hello") // main coroutine continues while the previous one is delayed
}

private suspend fun doWorld() = coroutineScope {
    // Concurrently executes both sections
    launch {
        delay(2000L)    // Non-blocking delay for 2s
        println("World 2!")
    }
    launch {
        delay(1000L)
        println("World 1!")
    }
    println("...")
}
