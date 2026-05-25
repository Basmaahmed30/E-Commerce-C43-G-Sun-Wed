package com.route.data.dataSource.utils

import com.google.gson.Gson
import com.route.data.model.auth.AuthResponseDM
import com.route.domain.model.Result
import com.route.domain.utils.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T, R> safeApiCall(
    apiCall: suspend () -> Response<T>,
    mapper: (T) -> R
): Flow<Result<R>> {
    return flow<Result<R>> {
        try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Result.Success(mapper(body)))
                } else {
                    emit(Result.Error(Failure.NoDataException))
                }
            } else {
                val gson = Gson()
                val errorBody =
                    gson.fromJson(response.errorBody()?.string(), AuthResponseDM::class.java)
                if (response.code() in 400..499)
                    emit(Result.Error(Failure.ClientException(errorBody.message ?: "")))
                else
                    emit(Result.Error(Failure.ServerException(errorBody.message ?: "")))
            }
        } catch (e: Exception) {
            emit(Result.Error(Failure.CustomException(e.message ?: "")))
        }
    }

}
