package com.tomaszezula.kotlin101.console

import java.io.ByteArrayOutputStream

class CapturedOutput(
    private val outBuffer: ByteArrayOutputStream,
    private val errBuffer: ByteArrayOutputStream,
) {
    val out: String
        get() = outBuffer.toString()
    val err: String
        get() = errBuffer.toString()
}