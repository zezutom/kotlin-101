package com.tomaszezula.kotlin101.messagequeue.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.tomaszezula.kotlin101.messagequeue.model.EventWrapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import kotlinx.coroutines.future.await

interface EventProducer {
    suspend fun publish(eventWrapper: EventWrapper)
}

@Service
class EventProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    @Value("\${spring.kafka.topic}") private val topic: String
) : EventProducer {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun publish(eventWrapper: EventWrapper) {
        logger.debug("Publishing event: {}", eventWrapper)
        val result = kafkaTemplate.send(topic, objectMapper.writeValueAsString(eventWrapper)).await()
        logger.debug("Published event: {}", result)
    }
}