package com.tomaszezula.kotlin101.coroutines.basics.blocking

import io.kotest.common.runBlocking
import io.kotest.matchers.longs.shouldBeBetween
import org.junit.jupiter.api.Test

class SuspendThreadExampleTest {

    @Test
    fun `should suspend the thread`(): Unit = runBlocking {
        val delayMs = 1000L
        suspendThread(delayMs).shouldBeBetween(delayMs, delayMs + 100)
    }
}