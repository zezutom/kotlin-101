package com.tomaszezula.kotlin101.console

import io.kotest.core.spec.style.StringSpec
import java.io.ByteArrayOutputStream
import java.io.PrintStream

abstract class ConsoleSpec(body: ConsoleSpec.(CapturedOutput) -> Unit = {}) : StringSpec({
    val originalOut = System.out
    val originalErr = System.err
    val outBuffer = ByteArrayOutputStream()
    val errBuffer = ByteArrayOutputStream()
    val capturedOutput = CapturedOutput(outBuffer, errBuffer)

    beforeSpec {
        System.setOut(PrintStream(outBuffer))
        System.setErr(PrintStream(errBuffer))
    }

    afterSpec {
        System.setOut(originalOut)
        System.setOut(originalErr)
    }

    beforeEach {
        outBuffer.reset()
        errBuffer.reset()
    }

    body(this as ConsoleSpec, capturedOutput)
})
