package com.tomaszezula.kotlin101.coroutines.basics.composition

import kotlinx.coroutines.*
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
    println("Sequential calls:")
    sequentialCalls()

    // Run the calls in parallel to get the answer as quickly as possible
    println("Parallel calls:")
    parallelCalls()

    // Run the calls in parallel, but start them lazily
    println("Parallel lazy calls:")
    parallelLazyCalls()
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

private suspend fun parallelLazyCalls() = coroutineScope {
    val time = measureTimeMillis {
        // The job won't start a coroutine until start() or await() is called
        val one = async(start = CoroutineStart.LAZY) { callA() }
        val two = async(start = CoroutineStart.LAZY) { callB() }

        // Start the coroutines to kick off parallel execution
        one.start()
        two.start()

        // Await the results. Please note that if the jobs are not started at this point, this will lead to sequential execution.
        println("The answer is ${one.await() + two.await()}")
    }
    println("Parallel calls completed in $time ms")
}
