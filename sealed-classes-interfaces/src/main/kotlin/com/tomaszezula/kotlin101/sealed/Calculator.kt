package com.tomaszezula.kotlin101.sealed

import com.tomaszezula.kotlin101.utils.tryRun

class Calculator {
    fun multiply(a: Int, b: Int): MultiplicationResult =
        tryRun(MultiplicationResult::Failure) {
            MultiplicationResult.Success(a * b.toLong())
        }

    fun divide(a: Int, b: Int): DivisionResult =
        tryRun(DivisionResult::Failure) {
            if (b == 0) {
                return@tryRun DivisionResult.Failure("Division by zero")
            } else {
                DivisionResult.Success(a.toDouble() / b)
            }
        }
}

sealed interface MultiplicationResult {
    data class Success(val response: Long) : MultiplicationResult
    data class Failure(val reason: String) : MultiplicationResult
}

sealed interface DivisionResult {
    data class Success(val response: Double) : DivisionResult
    data class Failure(val reason: String) : DivisionResult
}
