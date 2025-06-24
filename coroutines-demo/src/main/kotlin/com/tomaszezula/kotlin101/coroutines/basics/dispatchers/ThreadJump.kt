package com.tomaszezula.kotlin101.coroutines.basics.dispatchers

import com.tomaszezula.kotlin101.coroutines.jog
import com.tomaszezula.kotlin101.coroutines.log
import kotlinx.coroutines.*

/**
 * Demonstrates how to switch between different dispatchers (threads).
 * The first block runs in `ctx1`, then it switches to `ctx2` for a block of code,
 * and finally returns to `ctx1`.
 *
 * The example above demonstrates new techniques in coroutine usage.
 *
 * The first technique shows how to use runBlocking with a specified context.
 * The second technique involves calling withContext, which may suspend the current
 * coroutine and switch to a new context—provided the new context differs from the existing one.
 *
 * Specifically, if you specify a different CoroutineDispatcher, extra dispatches are required:
 * the block is scheduled on the new dispatcher, and once it finishes, execution returns to the original dispatcher.
 *
 * Source: https://kotlinlang.org/docs/coroutine-context-and-dispatchers.html#jumping-between-threads
 */
@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
fun main() {
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Started in ctx1")
                jog("Running in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                    jog("Finished in ctx2")
                }
                log("Back to ctx1")
                jog("Finished in ctx1")
            }
        }
    }
}
