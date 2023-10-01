package com.tomaszezula.kotlin101.coroutines.basics.scalability

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ManyThreadsExampleTest {

    @Test
    fun `should run out of memory`() {
        assertThrows<OutOfMemoryError> {
            startManyThreads(threadCount = 100_000)
        }
    }
}