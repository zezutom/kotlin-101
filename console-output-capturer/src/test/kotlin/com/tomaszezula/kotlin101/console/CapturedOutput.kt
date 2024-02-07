package com.tomaszezula.kotlin101.console

import java.io.ByteArrayOutputStream

class CapturedOutput(
    outBuffer: ByteArrayOutputStream,
    errBuffer: ByteArrayOutputStream,
) {
    val out = outBuffer
    val err = errBuffer
}