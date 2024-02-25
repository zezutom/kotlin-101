package com.tomaszezula.kotlin101.sequences

import kotlin.time.measureTimedValue

fun main() {
    val max = 1_000_000

    // List
    measureTimeAndMemory {
        (1..max)
            .filter { it % 2 == 0 }
            .filter { it % 11 == 0 }
            .take(3)
    }
    // Sequence
    measureTimeAndMemory {
        (1..max).asSequence()
            .filter { it % 2 == 0 }
            .filter { it % 11 == 0 }
            .take(3)
            .toList()
    }
}

private fun<T> measureTimeAndMemory(block: () -> T) {
    val runtime = Runtime.getRuntime()
    val usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory()
    val (result, duration) = measureTimedValue { block() }
    val usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory()
    println("Result: $result")
    println("Time: ${duration.inWholeMilliseconds} ms")
    println("Memory: ${usedMemoryAfter - usedMemoryBefore} bytes")
}