package com.tomaszezula.kotlin101.coroutines.basics.dispatchers

import com.tomaszezula.kotlin101.coroutines.jog
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * There are ways to intentionally break structured concurrency.
 */
fun main() = runBlocking {
    val request = launch {
        // This is a job independent of the parent job.
        launch(Job()) {
            jog("I run in my own Job and execute independently of the parent Job")
            delay(1000)
            jog("I am not affected by cancellation of the request")
        }

        launch {
            delay(100)
            jog("I run in the same Job as the request coroutine")
            delay(1000)
            jog("I will not execute this line if my parent request is cancelled")
        }
    }
    delay(500)
    request.cancel()    // Cancels the request job and its children
    jog("Who has survived cancellation?")
    delay(1000) // Wait for the independent child to finish
}
