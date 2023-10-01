package com.tomaszezula.kotlin101.coroutines.basics.blocking

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import kotlin.system.measureTimeMillis

/**
 * This function is used to block the thread.
 * Please don't use this approach in production.
 * It is used to demonstrate the impact of a bad practice
 * of running blocking code in coroutines.
 * Compare with [suspendThread]
 * @return the time it took to block the thread
 */
fun blockThread(delayMs: Long = 2000): Long {
    return measureTimeMillis {
        runBlocking {
            launch {
                sleep(delayMs)
                println("Coroutine #1: ${Thread.currentThread().name}")
            }
            launch {
                println("Coroutine #2: ${Thread.currentThread().name}")
            }
        }
    }
}
fun main() {
    val totalMs = blockThread()
    println("Hi from ${Thread.currentThread().name}. Processing took $totalMs ms")
}