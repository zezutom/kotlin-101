package com.tomaszezula.kotlin101.coroutines.sharedstate

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis


fun main() = runBlocking {
    val myState = MyState()

    // Start n coroutines that observe state updates
    repeat(3) {
        launch {
            myState.state.collect { (oldValue, newValue) ->
                println("Coroutine ${it + 1} receives state update - old value: $oldValue, new value: $newValue")
            }
        }
    }

    // Periodically increment the state
    parallelRun {
        delay(100)
        myState.increment()
    }
}

/**
 * This function launches a given suspending [action] [n] times in parallel,
 * and then waits for all the coroutines to complete.
 *
 * @param n number of coroutines to launch
 * @param k times an action is repeated by each coroutine
 * @param action the action to repeat
 */
suspend fun parallelRun(n: Int = 3, k: Int = 5, action: suspend () -> Unit) {
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}

data class StateUpdate(val oldValue: Int, val newValue: Int)

class MyState {
    private val _state = MutableSharedFlow<StateUpdate>(
        replay = 0, // No replay needed, we want to track changes from the moment we subscribe
        extraBufferCapacity = 64 // Some buffer capacity to avoid dropping updates
    )
    val state: SharedFlow<StateUpdate> get() = _state
    private var value = 0

    private val mutex = Mutex()

    suspend fun increment() {
        mutex.withLock {
            val oldValue = value
            val newValue = oldValue + 1
            _state.emit(StateUpdate(oldValue, newValue))
            value = newValue
        }
    }
}