package com.tomaszezula.kotlin101.console

import io.kotest.matchers.string.shouldContain

class TestOne : ConsoleSpec( { capturedOutput ->
    "should capture output" {
        println("Hello world")
        capturedOutput.out shouldContain  "Hello world"
    }
    "should capture error" {
        System.err.println("Too bad")
        capturedOutput.err shouldContain  "Too bad"
    }
})
