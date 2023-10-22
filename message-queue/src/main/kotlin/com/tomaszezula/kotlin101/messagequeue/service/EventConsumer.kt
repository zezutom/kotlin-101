package com.tomaszezula.kotlin101.messagequeue.service

import com.tomaszezula.kotlin101.messagequeue.model.EventWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

interface EventConsumer {
    suspend fun consume(eventWrapper: EventWrapper)
}

@Service
class EventConsumerImpl(
    private val eventValidator: EventValidator,
    private val eventHandler: EventHandler,
    private val dispatcher: CoroutineDispatcher
) : EventConsumer {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun consume(eventWrapper: EventWrapper): Unit =
        withContext(dispatcher + MDCContext()) {
            if (eventValidator.validate(eventWrapper)) {
                logger.debug("Consuming event: {}", eventWrapper)
                eventHandler.handle(eventWrapper.event)
            } else {
                logger.debug("Ignoring invalid event: {}", eventWrapper)
            }
        }
}