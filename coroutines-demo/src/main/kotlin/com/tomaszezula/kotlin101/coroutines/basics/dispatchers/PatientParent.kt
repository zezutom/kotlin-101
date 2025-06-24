package com.tomaszezula.kotlin101.coroutines.basics.dispatchers

import com.tomaszezula.kotlin101.coroutines.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val request = launch {
        repeat(3) { i -> // launch a few children jobs
            launch  {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                log("Coroutine $i is done")
            }
        }
        log("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join() // wait for completion of the request, including all its children
    log("Now processing of the request is complete")
}
