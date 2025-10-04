package com.dcs.core.domain

sealed class DomainResult<out T> {
    data class Success<T>(val data: T) : DomainResult<T>()
    data class Error(val exception: Throwable) : DomainResult<Nothing>()
}