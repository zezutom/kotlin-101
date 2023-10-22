package com.tomaszezula.kotlin101.messagequeue.service

import com.tomaszezula.kotlin101.messagequeue.model.EventWrapper
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

interface EventValidator {
    fun validate(event: EventWrapper): Boolean
}

@Service
class EventValidatorImpl : EventValidator {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun validate(event: EventWrapper): Boolean {
        logger.debug("Validating event: {}", event)
        // TODO validate event using the signature and the timestamp
        return true
    }
}