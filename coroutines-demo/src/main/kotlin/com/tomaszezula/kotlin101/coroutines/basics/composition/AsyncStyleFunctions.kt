package com.tomaszezula.kotlin101.coroutines.basics.composition

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * Async functions are popular in many programming languages, including JavaScript and Python.
 * However, in Kotlin this style is discouraged for several reasons:
 *
 * These functions are not suspending functions, which means they can be called from blocking code.
 * However, they rely on GlobalScope, which means they avoid structured concurrency.
 * They can lead to resource leaks if not handled properly, as they do not automatically cancel when the parent coroutine is cancelled.
 * They are considered "delicate" and require careful management to avoid issues such as resource leaks and unhandled exceptions.
 */
@OptIn(DelicateCoroutinesApi::class)
private fun callOneAsync(): Deferred<Int> = GlobalScope.async {
    callA()
}

@OptIn(DelicateCoroutinesApi::class)
private fun callTwoAsync(): Deferred<Int> = GlobalScope.async {
    callB()
}

private fun concurrentSumAsync() {
    val time = measureTimeMillis {
        // We can initiate outside a coroutine scope
        val one = callOneAsync()
        val two = callTwoAsync()

        // but waiting for a result requires a coroutine scope!
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Concurrent (async) calls completed in $time ms")
}

@OptIn(DelicateCoroutinesApi::class)
private fun failedConcurrentSumAsync() {
    val one = GlobalScope.async {
        try {
            delay(5_000)   // Simulate a long-running task
            42
        } catch (e: CancellationException) {
            println("Task one cancelled")
            throw e
        } finally {
            println("Task one finished")
        }
    }
    val two = GlobalScope.async<Int> {
        println("Task two throws exception")
        throw ArithmeticException("Simulated failure")
    }
    runBlocking {
        println("The answer is ${one.await() + two.await()}") // This will not be reached
    }
}

private suspend fun concurrentSum() = coroutineScope {
    val time = measureTimeMillis {
        val one = async { callA() }
        val two = async { callB() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Concurrent (coroutine scope) calls completed in $time ms")
}

private suspend fun failedConcurrentSum() = coroutineScope {
    val one = async {
        try {
            delay(Long.MAX_VALUE) // Simulate a long-running task
            42
        } catch (e: CancellationException) {
            println("Task one cancelled")
            throw e
        } finally {
            println("Task one finished")
        }
    }
    val two = async<Int> {
        println("Task two throws an exception")
        throw ArithmeticException("Simulated failure")
    }
    println("The answer is ${one.await() + two.await()}") // This will not be reached
}

fun main() {
    // This doesn't require a coroutine scope, but it does use GlobalScope.
    concurrentSumAsync()

    // Let's use a standard approach with structured concurrency for reliable cancellation and resource management.
    runBlocking {
        concurrentSum()
    }

    // Structured concurrency takes care of cancellation and resource management.
    runBlocking {
        try {
            failedConcurrentSum()
        } catch (e: Exception) {
            println("Calculation failed with exception: ${e.message}")
        }
    }

    // Global scope won't handle cancellation and leads to resource leak.
    runBlocking {
        try {
            failedConcurrentSumAsync()
        } catch (e: Exception) {
            println("Async calculation failed with exception: ${e.message}")
        }
    }
}
