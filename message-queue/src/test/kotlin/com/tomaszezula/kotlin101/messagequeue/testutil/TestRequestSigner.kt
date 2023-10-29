package com.tomaszezula.kotlin101.messagequeue.testutil

import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.Mac

@Service
class TestRequestSigner(private val hmacAlgorithm: Mac) {

    fun signData(data: String, timestamp: String): String {
        val dataToSign = "$timestamp.$data"
        val signedData = hmacAlgorithm.doFinal(dataToSign.toByteArray())
        return Base64.getEncoder().encodeToString(signedData)
    }
}