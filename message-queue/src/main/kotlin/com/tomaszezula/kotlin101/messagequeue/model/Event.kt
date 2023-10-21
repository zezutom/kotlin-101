package com.tomaszezula.kotlin101.messagequeue.model

data class Event(val header: Header, val message: String) {
    data class Header(val id: String, val timestamp: Long, val signature: String)
}
