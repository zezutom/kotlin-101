package com.tomaszezula.kotlin101.messagequeue.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.Mac

interface WebhookVerifier {
    fun isTrusted(timestamp: String, expectedSignature: String, data: String): Boolean
}

@Service
class WebhookVerifierImpl(private val hmacAlgorithm: Mac) : WebhookVerifier {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun isTrusted(timestamp: String, expectedSignature: String, data: String): Boolean {
        val dataSignature = signData(data, timestamp)
        return if (dataSignature == expectedSignature) {
            true
        } else {
            logger.debug("Invalid signature. Removing whitespaces from data and trying again")
            val dataWithoutWhitespaces = data.replace("\\s".toRegex(), "")
            if (signData(dataWithoutWhitespaces, timestamp) == expectedSignature) {
                true
            } else {
                logger.debug("invalid signature")
                false
            }
        }
    }

    private fun signData(data: String, timestamp: String): String {
        val dataToSign = "$timestamp.$data"
        val signedData = hmacAlgorithm.doFinal(dataToSign.toByteArray())
        return Base64.getEncoder().encodeToString(signedData)
    }
}