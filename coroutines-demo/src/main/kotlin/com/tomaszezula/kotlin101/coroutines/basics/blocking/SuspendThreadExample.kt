package com.tomaszezula.kotlin101.coroutines.basics.blocking

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * This function demonstrates how to suspend the thread with coroutines.
 * The objective is to prove that coroutines are lightweight, meaning that they don't block the thread.
 * The function launches two coroutines, one of which suspends the thread for 2 seconds.
 * This however has no impact on the other coroutine, which is executed immediately.
 * Compare with [blockThread]
 * @return the time it took to block the thread
 */
suspend fun suspendThread(delayMs: Long = 2000): Long {
    return measureTimeMillis {
        runBlocking {
            launch {
                delay(delayMs)
                println("Coroutine #1: ${Thread.currentThread().name}")
            }
            launch {
                println("Coroutine #2: ${Thread.currentThread().name}")
            }
        }
    }
}

fun main() = runBlocking {
    val totalMs = suspendThread()
    println("Hi from ${Thread.currentThread().name}. Processing took $totalMs ms")
}