package com.tomaszezula.kotlin101.messagequeue.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.tomaszezula.kotlin101.messagequeue.model.Event
import com.tomaszezula.kotlin101.messagequeue.service.EventProducer
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.coroutines.cancellation.CancellationException

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
    @PostMapping
    suspend fun handleWebhook(
        request: ServletServerHttpRequest,
        @RequestHeader(TIMESTAMP_HEADER) timestampHeader: String,
        @RequestHeader(SIG_HEADER) sigHeader: String,
    ): ResponseEntity<Unit> {
        try {
            val event = objectMapper.readValue(request.body, Event::class.java)
            eventProducer.publish(event)
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            println("Ignoring invalid event, reason: ${t.message}")
        }
        return ResponseEntity.ok().build()
    }
}