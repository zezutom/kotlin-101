package com.tomaszezula.kotlin101.coroutines.basics.dispatchers

import com.tomaszezula.kotlin101.coroutines.log
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

/**
 * Run it with -Dkotlinx.coroutines.debug
 */
fun main() = runBlocking {
    val a = async {
        log("I'm computing a piece of answer")
        6
    }

    val b = async {
        log("I'm computing another piece of answer")
        7
    }
    log("The answer is ${a.await() * b.await()}")
}
