package com.tomaszezula.kotlin101.messagequeue.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.tomaszezula.kotlin101.messagequeue.model.EventWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaListener(
    private val eventConsumer: EventConsumer,
    private val objectMapper: ObjectMapper,
    private val dispatcher: CoroutineDispatcher
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["\${spring.kafka.topic}"], groupId = "kotlin101")
    fun listen(message: String) = runBlocking {
        logger.debug("Received message: {}", message)
        val eventWrapper = objectMapper.readValue(message, EventWrapper::class.java)
        launch { consume(eventWrapper) }
    }

    private suspend fun consume(eventWrapper: EventWrapper) {
        withContext(dispatcher + MDCContext()) {
            eventConsumer.consume(eventWrapper)
        }
    }

}
