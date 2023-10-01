package com.tomaszezula.kotlin101.coroutines.basics.scalability

import java.lang.Thread.sleep
import kotlin.concurrent.thread

/**
 * This function proves that working with JVM threads is not scalable.
 * It is easy to run out of memory when creating too many threads.
 * Please don't run this code in production.
 * Compare with [startManyCoroutines].
 */
fun startManyThreads(threadCount: Int = 50_000, delayMs: Long = 5000) {
    repeat(threadCount) {
        thread {
            sleep(delayMs)
        }
    }
}

fun main() {
    startManyThreads()
    println("Hi from ${Thread.currentThread().name}")
}