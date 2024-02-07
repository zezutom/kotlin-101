package com.tomaszezula.kotlin101.sealed.tutorial.example04.plugins

import com.tomaszezula.kotlin101.sealed.tutorial.example04.service.ServiceResult
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> resultOf(call: () -> HttpResponse): ServiceResult<T> =
    try {
        call().let { response ->
            if (response.status.value in 200..299) {
                ServiceResult.Success(response.body())
            } else {
                ServiceResult.Failure(
                    reason = response.status.description,
                    status = response.status.value
                )
            }
        }
    } catch (e: CancellationException) {
        throw e
    } catch (t: Throwable) {
        ServiceResult.Failure(
            reason = t.message ?: "Unknown error",
            status = 500
        )
    }
