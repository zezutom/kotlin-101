package com.tomaszezula.kotlin101.sealed.http

interface ApiCallResult<out T> {
    data class Success<T>(val data: T) : ApiCallResult<T>
    data class Failure(val reason: String) : ApiCallResult<Nothing>
    object BadRequest : ApiCallResult<Nothing>
    object Unauthorized : ApiCallResult<Nothing>
    object NotFound : ApiCallResult<Nothing>
}
