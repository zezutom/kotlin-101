package com.tomaszezula.kotlin101.coroutines

import kotlinx.coroutines.Job
import kotlin.coroutines.coroutineContext

fun log(message: String) {
    println("[${Thread.currentThread().name}] $message")
}

/**
 * Logs a message with the current coroutine context and info about the job.
 * This is useful for debugging and understanding coroutine execution flow.
 * Job is part of the coroutine context and can be used to track the lifecycle
 * of the coroutine.
 * @param message The message to log, typically describing the coroutine's action or state.
 * This function is a suspend function, meaning it can be called from within a coroutine.
 */
suspend fun jog(message: String) {
    println("[${coroutineContext[Job]}] $message")
}
