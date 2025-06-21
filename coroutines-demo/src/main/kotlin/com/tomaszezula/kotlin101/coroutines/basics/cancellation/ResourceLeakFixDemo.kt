package com.tomaszezula.kotlin101.coroutines.basics.cancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout

fun main() {
    runBlocking {
        repeat(10_000) {    // Launch a lot of coroutines
            launch {
                // Use a local variable to hold the resource
                var resource: Resource? = null
                try {
                    withTimeout(51) {    // Timeout is tight!
                        delay(50)
                        resource = Resource()  // Acquire the resource
                    }
                } finally {
                    // This might not execute if the timeout occurs!
                    resource?.close()  // Release the resource
                }
            }
        }
    }

    // Wait for a while to let the coroutines finish
    println(acquired)
}

