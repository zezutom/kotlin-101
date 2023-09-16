package com.tomaszezula.kotlin101.sealed

import com.tomaszezula.kotlin101.sealed.CalculationResult.Failure
import com.tomaszezula.kotlin101.sealed.CalculationResult.Success
import com.tomaszezula.kotlin101.utils.tryRun

class CalculatorImproved {
    fun multiply(a: Int, b: Int): CalculationResult<Long> =
        tryRun(::Failure) {
            Success(a * b.toLong())
        }

    fun divide(a: Int, b: Int): CalculationResult<Double> =
        tryRun(::Failure) {
            if (b == 0) {
                return@tryRun Failure("Division by zero")
            } else {
                Success(a.toDouble() / b)
            }
        }
}

sealed interface CalculationResult<out T> {
    data class Success<T>(val response: T) : CalculationResult<T>
    data class Failure(val reason: String) : CalculationResult<Nothing>
}

