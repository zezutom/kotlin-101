package com.tomaszezula.kotlin101.messagequeue.model

data class EventWrapper(val timestamp: Long, val signature: String, val event: Event) {
    companion object {
        fun create(timestamp: String, signature: String, event: Event): EventWrapper {
            return EventWrapper(timestamp.toLong(), signature, event)
        }
    }
}