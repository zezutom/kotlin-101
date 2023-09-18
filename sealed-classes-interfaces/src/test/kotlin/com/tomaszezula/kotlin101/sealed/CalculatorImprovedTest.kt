package com.tomaszezula.kotlin101.sealed

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CalculatorImprovedTest : FunSpec({
    lateinit var calculator: CalculatorImproved

    beforeSpec { calculator = CalculatorImproved() }

    test("should multiply two integers") {
        calculator.multiply(2, 3) shouldBe CalculationResult.Success(6L)
    }

    test("should preserve the sign when multiplying two integers") {
        calculator.multiply(-2, 3) shouldBe CalculationResult.Success(-6L)
    }

    test("should divide two integers") {
        calculator.divide(6, 3) shouldBe CalculationResult.Success(2.0)
    }

    test("should preserve the sign when dividing two integers") {
        calculator.divide(-6, 3) shouldBe CalculationResult.Success(-2.0)
    }

    test("division by zero should fail") {
        calculator.divide(6, 0) shouldBe CalculationResult.Failure("Division by zero")
    }
})