package com.tomaszezula.kotlin101.sequences

fun main() {
    // Let's say we start with a collection of numbers and want to find
    // the first negative number after squaring each number. This is
    // intentionally a nonsensical operation to demonstrate the misuse of sequences.

    val numbers = listOf(1, 2, 3, 4, -5, 6, -7)

    // List approach:
    val firstNegativeSquaredList = numbers.map { it * it }.firstOrNull { it < 0 }
    println("List result: $firstNegativeSquaredList")

    // Sequence approach:
    val firstNegativeSquaredSequence = numbers.asSequence().map { it * it }.firstOrNull { it < 0 }
    println("Sequence result: $firstNegativeSquaredSequence")
}
