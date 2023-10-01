package com.tomaszezula.kotlin101.coroutines.basics.scalability

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
class ManyCoroutinesExampleTest {

    @Test
    fun `should handle the load`() {
        val singleThreadPool = newSingleThreadContext("test")
        runBlocking(singleThreadPool) {
            startManyCoroutines(scope = this, coroutineCount = 100_000)
        }
    }
}