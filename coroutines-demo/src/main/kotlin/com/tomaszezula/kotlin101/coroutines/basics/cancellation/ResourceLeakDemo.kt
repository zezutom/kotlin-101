package com.tomaszezula.kotlin101.coroutines.basics.cancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

// A shared resource
var acquired = 0

fun main() {

    runBlocking {
        repeat(10_000) {    // Launch a lot of coroutines
            launch {
                val resource = withTimeout(51) {    // Timeout is tight!
                    delay(50)
                    Resource()  // Acquire the resource
                }

                // This might not execute if the timeout occurs!
                resource.close()  // Release the resource
            }
        }
    }

    // Wait for a while to let the coroutines finish
    println(acquired)
}

class Resource {
    init {
        acquired++  // Acquire the resource
    }
    fun close() {
        acquired--  // Release the resource
    }
}
