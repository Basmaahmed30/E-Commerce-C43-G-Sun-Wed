package com.route.data.di

import android.util.Log
import com.route.domain.model.Result
import com.route.domain.repository.AuthLocalDataSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class TokenManager @Inject constructor(private val authLocalDataSource: AuthLocalDataSource) {
    private var cachedToken: String? = null
    suspend fun getToken(): String? {
        if (cachedToken != null) return cachedToken
        val tokenState = authLocalDataSource.getToken().firstOrNull()
        when (tokenState) {
            is Result.Error -> {
                Log.e("TOKEN", "getToken: ERROR ${tokenState.failure.message}")
                return null
            }

            is Result.Success -> {
                cachedToken = tokenState.data
                return cachedToken
            }

            null -> {
                return null
            }
        }

    }
}