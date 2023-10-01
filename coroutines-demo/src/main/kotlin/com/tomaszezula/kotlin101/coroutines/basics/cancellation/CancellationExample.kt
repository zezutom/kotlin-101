package com.tomaszezula.kotlin101.coroutines.basics.cancellation

import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

fun main() = runBlocking {
    val result = getFastestResponse(this)
    println("Result: $result")
}

suspend fun getFastestResponse(
    scope: CoroutineScope,
    concurrency: Int = 3,
    minDelay: Long = 100,
    maxDelay: Long = 500
): String {
    val jobs = (1..concurrency).map { i ->
        scope.async {
            getDelayedMessage(
                "Hello from coroutine #$i",
                Random.nextLong(minDelay, maxDelay)
            )
        }
    }
    return select {
        jobs.forEach { job -> job.onAwait { it } }
    }.also {
        coroutineContext.cancelChildren()
    }
}

suspend fun getDelayedMessage(message: String, delayMs: Long): String {
    delay(delayMs)
    return message
}