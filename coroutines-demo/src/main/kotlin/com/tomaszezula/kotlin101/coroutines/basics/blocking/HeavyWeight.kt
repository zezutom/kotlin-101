package com.tomaszezula.kotlin101.coroutines.basics.blocking

fun main() {
    repeat(50_000) {
        Thread.sleep(2000)
        print(".")
    }
}
