package com.tomaszezula.kotlin101.messagequeue.service

import com.tomaszezula.kotlin101.messagequeue.model.Event
import org.springframework.stereotype.Service

interface EventConsumer {
    suspend fun consume(event: Event)
}

@Service
class EventConsumerImpl(private val eventValidator: EventValidator) : EventConsumer {
    override suspend fun consume(event: Event) {
        if (eventValidator.validate(event).not()) {
            println("Ignoring invalid event: $event")
        } else {
            println("Consuming event: $event")
        }
    }
}