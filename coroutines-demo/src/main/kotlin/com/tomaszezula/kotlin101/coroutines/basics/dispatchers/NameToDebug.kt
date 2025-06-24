package com.tomaszezula.kotlin101.coroutines.basics.dispatchers

import com.tomaszezula.kotlin101.coroutines.log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    log("Started main coroutine")

    val one = async(CoroutineName("One")) {
        delay(500)
        log("Running in coroutine One")
        13
    }
    val two = async(CoroutineName("Two")) {
        delay(1000)
        log("Computing in coroutine Two")
        29
    }
    log("The answer is ${one.await() + two.await()}")
}
