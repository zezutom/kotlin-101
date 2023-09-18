package com.tomaszezula.kotlin101.sealed

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe


class CalculatorTest : FunSpec({
    lateinit var calculator: Calculator

    beforeSpec { calculator = Calculator() }

    test("should multiply two integers") {
        calculator.multiply(2, 3) shouldBe MultiplicationResult.Success(6)
    }

    test("should preserve the sign when multiplying two integers") {
        calculator.multiply(-2, 3) shouldBe MultiplicationResult.Success(-6)
    }

    test("should divide two integers") {
        calculator.divide(6, 3) shouldBe DivisionResult.Success(2.0)
    }

    test("should preserve the sign when dividing two integers") {
        calculator.divide(-6, 3) shouldBe DivisionResult.Success(-2.0)
    }

    test("division by zero should fail") {
        calculator.divide(6, 0) shouldBe DivisionResult.Failure("Division by zero")
    }
})