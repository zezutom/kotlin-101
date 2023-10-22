package com.tomaszezula.kotlin101.messagequeue.service

import com.tomaszezula.kotlin101.messagequeue.model.Event
import kotlinx.coroutines.delay
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

interface EventHandler {
    suspend fun handle(event: Event)
}

@Service
class TimeConsumingEventHandler : EventHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun handle(event: Event) {
        delay(5000)
        logger.debug("Processed event: {}", event)
    }
}