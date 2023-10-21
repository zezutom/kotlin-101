package com.tomaszezula.kotlin101.messagequeue.service

import com.tomaszezula.kotlin101.messagequeue.model.Event
import org.springframework.stereotype.Service

interface EventValidator {
    fun validate(event: Event): Boolean
}

@Service
class EventValidatorImpl : EventValidator {
    override fun validate(event: Event): Boolean {
        return true
    }
}