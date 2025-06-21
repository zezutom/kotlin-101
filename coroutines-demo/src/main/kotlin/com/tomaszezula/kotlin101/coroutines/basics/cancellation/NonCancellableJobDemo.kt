package com.tomaszezula.kotlin101.coroutines.basics.cancellation

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        var nextPrintTime = System.currentTimeMillis()
        var i = 0

        // Replace with while(isActive) to make it cancellable
        while (i < 5) { // computation loop, just wastes CPU
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job: I'm sleeping $i ...")
                nextPrintTime += 500L // 500 ms
                i++
            }
        }
    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job and waits for its completion
    println("main: Now I can quit.")
}
