package com.route.domain.utils

sealed class Failure(val message: String? = null) {
    data object NoDataException : Failure()
    data class ServerException(val errorMessage: String? = null) : Failure(errorMessage)
    data class ClientException(val errorMessage: String? = null) : Failure(errorMessage)
    data class CustomException(val errorMessage: String? = null) : Failure(errorMessage)
}
