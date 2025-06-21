package com.tomaszezula.kotlin101.coroutines.basics.composition

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

suspend fun callA(): Int {
    delay(1000L)    // Simulate a long-running operation
    return 13
}

suspend fun callB(): Int {
    delay(1000L)    // Simulate a long-running operation
    return 29
}

fun main() = runBlocking {
    // Make sequential calls to callA and callB => total processing time is the total of both calls
    sequentialCalls()

    // Run the calls in parallel to get the answer as quickly as possible
    parallelCalls()
}
private suspend fun sequentialCalls() {
    val time = measureTimeMillis {
        val one = callA()
        val two = callB()
        println("The answer is ${one + two}")
    }
    println("Sequential calls completed in $time ms")
}

private suspend fun parallelCalls() = coroutineScope {
    val time = measureTimeMillis {
        val one = async { callA() }
        val two = async { callB() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("Parallel calls completed in $time ms")
}
