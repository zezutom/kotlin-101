package com.tomaszezula.kotlin101.coroutines.basics.dispatchers

import kotlinx.coroutines.*

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun main() = runBlocking<Unit> {
    launch {
        printWhereYouAre("main runBlocking            ")
        delay(1000)
        printWhereYouAre("main runBlocking after delay")
    }
    launch(Dispatchers.Unconfined) {
        printWhereYouAre("Unconfined                  ")
        delay(500)
        printWhereYouAre("Unconfined after delay      ")
    }
    launch(Dispatchers.Default) {
        printWhereYouAre("Default                     ")
    }
    launch(newSingleThreadContext("newSingleThreadContext")) {
        printWhereYouAre("newSingleThreadContext      ")
    }
}

private fun printWhereYouAre(name: String) {
    println("$name: I'm working in thread ${Thread.currentThread().name}")
}
