package com.tomaszezula.kotlin101.coroutines.basics.blocking

import io.kotest.matchers.longs.shouldBeBetween
import org.junit.jupiter.api.Test

class BlockThreadExampleTest {

    @Test
    fun `should block the thread`() {
        val delayMs = 1000L
        blockThread(delayMs).shouldBeBetween(delayMs, delayMs + 100)
    }
}