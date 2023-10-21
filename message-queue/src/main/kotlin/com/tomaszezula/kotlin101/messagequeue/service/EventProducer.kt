package com.tomaszezula.kotlin101.messagequeue.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.tomaszezula.kotlin101.messagequeue.model.Event
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

interface EventProducer {
    suspend fun publish(event: Event)
}

@Service
class EventProducerImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    @Value("\${spring.kafka.topic}") private val topic: String
) : EventProducer {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override suspend fun publish(event: Event) {
        logger.debug("Publishing event: {}", event)
        kafkaTemplate.send(topic, objectMapper.writeValueAsString(event))
    }
}