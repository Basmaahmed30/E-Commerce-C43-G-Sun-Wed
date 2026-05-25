package com.route.domain.model

import com.route.domain.utils.Failure

sealed class Result<T> {
    data class Success<T>(val data: T? = null) : Result<T>()
    data class Error<T>(val failure: Failure) : Result<T>()
}