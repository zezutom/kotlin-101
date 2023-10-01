package com.tomaszezula.kotlin101.coroutines.basics.scalability

import kotlinx.coroutines.*

/**
 * This function proves that coroutines, compared to threads, are cheap to create.
 * Despite a higher number of launched coroutines the program successfully completes.
 * Compare with [startManyThreads].
 */
suspend fun startManyCoroutines(scope: CoroutineScope, coroutineCount: Int = 50_000, delayMs: Long = 5000) {
    repeat(coroutineCount) {
        scope.launch {
            delay(delayMs)
        }
    }
}

fun main() = runBlocking {
    startManyCoroutines(this)
    println("Hi from ${Thread.currentThread().name}")
}
