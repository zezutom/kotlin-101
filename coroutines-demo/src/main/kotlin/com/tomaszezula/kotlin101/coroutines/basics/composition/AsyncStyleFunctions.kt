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
fun callOneAsync(): Deferred<Int> = GlobalScope.async {
    callA()
}

@OptIn(DelicateCoroutinesApi::class)
fun callTwoAsync(): Deferred<Int> = GlobalScope.async {
    callB()
}

fun main() {
    // This doesn't require a coroutine scope, but it does use GlobalScope.
    concurrentSumAsync()

    // Let's use a standard approach with structured concurrency for reliable cancellation and resource management.
    runBlocking {
        concurrentSum()
    }
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

private suspend fun concurrentSum() = coroutineScope {
    val time = measureTimeMillis {
        val one = async { callA() }
        val two = async { callB() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Concurrent (coroutine scope) calls completed in $time ms")
}
