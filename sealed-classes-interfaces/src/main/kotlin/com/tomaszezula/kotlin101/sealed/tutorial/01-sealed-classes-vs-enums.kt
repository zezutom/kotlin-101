package com.tomaszezula.kotlin101.sealed.tutorial

enum class EnumResult {
    Success,
    Failure
}

sealed interface SealedResult {
    data class Success(val message: String, val bonus: Int) : SealedResult
    data class Failure(val error: String) : SealedResult
}

fun doEnumStuff(x: Int): EnumResult {
    return if (x > 0) {
        EnumResult.Success
    } else {
        EnumResult.Failure
    }
}

fun doSealedStuff(x: Int): SealedResult {
    return if (x > 0) {
        SealedResult.Success("Enjoy your bonus!", x * 2)
    } else {
        SealedResult.Failure("Expected a positive number, got $x")
    }
}

fun main() {
    when (doEnumStuff(10)) {
        EnumResult.Success -> println("Success")
        EnumResult.Failure -> println("Failure")
    }

    when (val sealedResult = doSealedStuff(10)) {
        is SealedResult.Success -> println("Success: ${sealedResult.message}, bonus: ${sealedResult.bonus}")
        is SealedResult.Failure -> println("Failure")
    }
}