package com.tomaszezula.kotlin101.messagequeue.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.tomaszezula.kotlin101.messagequeue.model.Event
import com.tomaszezula.kotlin101.messagequeue.model.EventWrapper
import com.tomaszezula.kotlin101.messagequeue.service.EventProducer
import kotlinx.coroutines.reactive.awaitFirst
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.measureTimeMillis


@RestController
@RequestMapping("/webhook")
class WebhookController(
    private val eventProducer: EventProducer,
    private val objectMapper: ObjectMapper
) {
    companion object {
        const val TIMESTAMP_HEADER = "x-custom-request-timestamp"
        const val SIG_HEADER = "x-custom-signature"
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    @PostMapping
    suspend fun handleWebhook(
        request: ServerHttpRequest,
        @RequestHeader(TIMESTAMP_HEADER) timestampHeader: String,
        @RequestHeader(SIG_HEADER) sigHeader: String,
    ): ResponseEntity<Unit> {
        measureTimeMillis {
            try {
                val requestBody = request.body.single().awaitFirst()
                val event = objectMapper.readValue(requestBody.asInputStream(), Event::class.java)
                val eventWrapper = EventWrapper.create(timestampHeader, sigHeader, event)
                eventProducer.publish(eventWrapper)
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                logger.debug("Ignoring invalid event, reason: {}", t.message)
            }
            return ResponseEntity.ok().build()
        }
    }
}