package com.tomaszezula.kotlin101.messagequeue.service

import com.tomaszezula.kotlin101.messagequeue.config.TestConfig
import com.tomaszezula.kotlin101.messagequeue.testutil.TestRequestSigner
import com.tomaszezula.kotlin101.messagequeue.testutil.currentTimestamp
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.time.Clock

@SpringBootTest
@Import(TestConfig::class)
class WebhookVerifierTest {

    @Autowired
    private lateinit var verifier: WebhookVerifier

    @Autowired
    private lateinit var testRequestSigner: TestRequestSigner

    @Autowired
    private lateinit var constantClock: Clock

    @Test
    fun `should recognize valid request`() {
        val payload = "hello world!"
        val expectedSignature = testRequestSigner.signData(payload, constantClock.currentTimestamp())
        assertThat(verifier.isTrusted(constantClock.currentTimestamp(), expectedSignature, payload)).isTrue()
    }

    @Test
    fun `should mark request with invalid signature as invalid`() {
        val payload = "hello world!"
        val wrongTimestamp = constantClock.instant().epochSecond.plus(1).toString()
        val expectedSignature = testRequestSigner.signData(payload, constantClock.currentTimestamp())
        assertThat(verifier.isTrusted(wrongTimestamp, expectedSignature, payload)).isFalse()
    }

    @Test
    fun `should mark request as valid if there are extra whitespaces in data`() {
        val payload = "   hello world!  "
        val expectedSignature = testRequestSigner.signData(payload.trim(), constantClock.currentTimestamp())
        val wrongTimestamp = constantClock.instant().epochSecond.plus(1).toString()
        assertThat(verifier.isTrusted(wrongTimestamp, expectedSignature, payload)).isFalse()
    }
}