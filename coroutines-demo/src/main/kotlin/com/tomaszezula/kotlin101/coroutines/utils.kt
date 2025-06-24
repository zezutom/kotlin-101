package com.tomaszezula.kotlin101.coroutines

fun log(message: String) {
    println("[${Thread.currentThread().name}] $message")
}
