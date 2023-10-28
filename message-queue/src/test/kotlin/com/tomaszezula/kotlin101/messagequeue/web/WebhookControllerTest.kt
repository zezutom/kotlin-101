package com.tomaszezula.kotlin101.messagequeue.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.tomaszezula.kotlin101.messagequeue.model.Event
import com.tomaszezula.kotlin101.messagequeue.testutil.TestRequestSigner
import com.tomaszezula.kotlin101.messagequeue.testutil.currentTimestamp
import com.tomaszezula.kotlin101.messagequeue.web.WebhookController.Companion.SIG_HEADER
import com.tomaszezula.kotlin101.messagequeue.web.WebhookController.Companion.TIMESTAMP_HEADER
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Clock


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebhookControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var testRequestSigner: TestRequestSigner

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var constantClock: Clock

    private lateinit var eventData: String

    @BeforeEach
    fun setUp() {
        val event = Event("test-event-id", "hello world!")
        eventData = objectMapper.writeValueAsString(event)
    }

    @Test
    fun `should return 200 when request is valid`() {
        val timestamp = constantClock.currentTimestamp()
        val expectedSignature = testRequestSigner.signData(eventData, timestamp)
        verifyWebhookCall(
            expectedSignature = expectedSignature,
            timestamp = timestamp
        ) {
            it.expectStatus().isOk
        }
    }

    @Test
    fun `should return 4xx when wrong timestamp header is passed`() {
        val timestamp = constantClock.instant().epochSecond.minus(1).toString()
        val expectedSignature = testRequestSigner.signData(eventData, constantClock.currentTimestamp())
        verifyWebhookCall(
            expectedSignature = expectedSignature,
            timestamp = timestamp
        ) {
            it.expectStatus().is4xxClientError
        }
    }

    @Test
    fun `should return 4xx when wrong signature header is passed`() {
        val timestamp = constantClock.currentTimestamp()
        verifyWebhookCall(
            expectedSignature = "wrong-signature",
            timestamp = timestamp
        ) {
            it.expectStatus().is4xxClientError
        }
    }

    @Test
    fun `should return 400 when timestamp header is missing`() {
        val expectedSignature = testRequestSigner.signData(eventData, constantClock.currentTimestamp())
        verifyWebhookCall(
            expectedSignature = expectedSignature,
            timestamp = null
        ) {
            it.expectStatus().isBadRequest
        }
    }

    @Test
    fun `should return 400 when signature header is missing`() {
        verifyWebhookCall(
            expectedSignature = null,
            timestamp = constantClock.currentTimestamp()
        ) {
            it.expectStatus().isBadRequest
        }
    }

    private fun verifyWebhookCall(
        data: String = eventData,
        expectedSignature: String? = null,
        timestamp: String? = null,
        verify: (WebTestClient.ResponseSpec) -> Unit
    ) {
        val req = webTestClient.post().uri("/webhook").bodyValue(data)
        if (expectedSignature != null) {
            req.header(SIG_HEADER, expectedSignature)
        }
        if (timestamp != null) {
            req.header(TIMESTAMP_HEADER, timestamp)
        }
        verify(req.exchange())
    }
}