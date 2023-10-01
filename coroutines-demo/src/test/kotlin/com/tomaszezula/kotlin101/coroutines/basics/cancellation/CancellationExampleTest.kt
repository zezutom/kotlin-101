package com.tomaszezula.kotlin101.coroutines.basics.cancellation

import io.kotest.matchers.longs.shouldBeBetween
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class CancellationExampleTest {

    @Test
    fun `should return the fastest response`() {
        val minDelay = 100L
        val maxDelay = 300L
        val resultMs = measureTimeMillis {
            runBlocking {
                val result = getFastestResponse(
                    scope = this,
                    concurrency = 3,
                    minDelay = minDelay,
                    maxDelay = maxDelay
                )
                println(result)
            }
        }
        resultMs.shouldBeBetween(minDelay, maxDelay)
    }
}