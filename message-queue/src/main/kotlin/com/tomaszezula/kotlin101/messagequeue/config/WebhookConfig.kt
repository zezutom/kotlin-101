package com.tomaszezula.kotlin101.messagequeue.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Configuration
class WebhookConfig {

    @Value("\${webhook.secret}")
    lateinit var secret: String

    @Bean
    fun hmacAlgorithm(): Mac {
        val hmacSha256 = "HmacSHA256"
        val hmac = Mac.getInstance(hmacSha256)
        val secretKeySpec = SecretKeySpec(secret.toByteArray(), hmacSha256)
        hmac.init(secretKeySpec)
        return hmac
    }
}