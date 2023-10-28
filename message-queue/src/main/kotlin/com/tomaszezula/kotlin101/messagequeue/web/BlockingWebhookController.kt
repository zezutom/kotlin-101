package com.tomaszezula.kotlin101.messagequeue.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.tomaszezula.kotlin101.messagequeue.model.Event
import com.tomaszezula.kotlin101.messagequeue.model.EventWrapper
import com.tomaszezula.kotlin101.messagequeue.service.EventHandler
import com.tomaszezula.kotlin101.messagequeue.service.WebhookVerifier
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
@RequestMapping("/webhook/blocking")
class BlockingWebhookController(
    private val webhookVerifier: WebhookVerifier,
    private val eventHandler: EventHandler,
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
            return try {
                val requestBody = request.body.single().awaitFirst().toString(Charsets.UTF_8)
                logger.debug("Received webhook request: {}", requestBody)
                if (webhookVerifier.isTrusted(timestampHeader, sigHeader, requestBody)) {
                    val event = objectMapper.readValue(requestBody, Event::class.java)
                    val eventWrapper = EventWrapper.create(timestampHeader, sigHeader, event)
                    eventHandler.handle(eventWrapper.event)
                    ResponseEntity.ok().build()
                } else {
                    logger.debug("Ignoring untrusted webhook request")
                    ResponseEntity.status(403).build()
                }
            } catch (e: CancellationException) {
                throw e
            } catch (t: Throwable) {
                logger.debug("Ignoring invalid event, reason: {}", t.message)
                ResponseEntity.status(403).build()
            }
        }
    }
}