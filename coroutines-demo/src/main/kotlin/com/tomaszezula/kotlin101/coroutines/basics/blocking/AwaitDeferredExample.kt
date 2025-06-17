package com.tomaszezula.kotlin101.coroutines.basics.blocking

import kotlinx.coroutines.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory


val log: Logger = LoggerFactory.getLogger("Main")

fun main() = runBlocking {
    log.info("Running coroutines on main thread")
    awaitSingleOnMainThread()
    log.info("Running coroutines on main and worker thread")
    awaitSingleOnWorkerThread()
    log.info("Running multiple concurrent operations on main and worker thread")
    awaitMultipleOnWorkerThread()
}

private suspend fun awaitSingleOnMainThread() = coroutineScope {
    val deferred = async {
        loadData()
    }
    log.info("Waiting ...")
    log.info("Result: {}", deferred.await())
}

private suspend fun awaitSingleOnWorkerThread() = coroutineScope {
    val deferred = async(Dispatchers.Default) {
        loadData()
    }
    log.info("Waiting ...")
    log.info("Result: {}", deferred.await())
}

private suspend fun awaitMultipleOnWorkerThread() = coroutineScope {
    val deferreds = (1..3).map {
        async(Dispatchers.IO) {
            delay(1000L * it)
            log.info("Loading {}", it)
            it
        }
    }
    val sum = deferreds.awaitAll().sum()
    log.info("Sum: {}", sum)
}

private suspend fun loadData(): Int {
    log.info("Loading ...")
    delay(1000L)
    log.info("Loaded!")
    return 42
}
