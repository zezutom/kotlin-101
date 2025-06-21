package com.tomaszezula.kotlin101.coroutines.sharedstate

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    // This is a simple Rendezvous Channel
    val channel = Channel<String>()

    launch {
        log("Publishing A1")
        channel.send("A1")
        log("Publishing A2")
        channel.send("A2")
        log("A done")
    }

    launch {
        log("Publishing B1")
        channel.send("B1")
        log("B done")
    }

    launch {
        repeat(3) {
            log("Waiting for a message")
            val x = channel.receive()
            log(x)
        }
    }
}

fun log(msg: String) {
    println("[${Thread.currentThread().name}] $msg")
}
