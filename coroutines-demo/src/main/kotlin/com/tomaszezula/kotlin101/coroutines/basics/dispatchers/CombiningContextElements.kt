package com.tomaszezula.kotlin101.coroutines.basics.dispatchers

import com.tomaszezula.kotlin101.coroutines.log
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    launch(Dispatchers.Default + CoroutineName("FirstCoroutine")) {
        log("Running in the first coroutine")
    }
    launch(Dispatchers.IO + CoroutineName("SecondCoroutine")) {
        log("Running in the second coroutine")
    }

}
