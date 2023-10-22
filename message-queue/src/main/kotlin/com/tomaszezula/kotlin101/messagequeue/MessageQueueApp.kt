package com.tomaszezula.kotlin101.messagequeue

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MessageQueueApp {
}

fun main() {
    runApplication<MessageQueueApp>()
}