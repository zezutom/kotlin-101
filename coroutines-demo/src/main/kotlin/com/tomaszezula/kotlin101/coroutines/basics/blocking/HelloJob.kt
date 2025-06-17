package com.tomaszezula.kotlin101.coroutines.basics.blocking

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch {
        delay(1000)
        println("World")
    }
    println("Hello")
    job.join() // Wait until done
    println("Done")
}

