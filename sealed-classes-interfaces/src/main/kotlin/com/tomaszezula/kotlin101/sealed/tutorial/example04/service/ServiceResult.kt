package com.tomaszezula.kotlin101.sealed.tutorial.example04.service

interface ServiceResult<out T> {
    data class Success<T>(val data: T) : ServiceResult<T>
    data class Failure(val reason: String?, val status: Int) : ServiceResult<Nothing>
}
