package com.tomaszezula.kotlin101.coroutines.basics.cancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch {
        repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(1000L)
        }
    }
    delay(3000L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancel() // cancels the job
    job.join() // waits for job's completion
//    job.cancelAndJoin()   // alternative to cancel and join
    println("main: Now I can quit.")
}
