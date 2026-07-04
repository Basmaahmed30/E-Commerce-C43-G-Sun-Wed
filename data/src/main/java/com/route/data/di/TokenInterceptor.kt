package com.route.data.di

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {
    val TOKEN_KEY = "token"
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request().newBuilder()
        val token = runBlocking { tokenManager.getToken() }
        if (token != null)
            originalRequest.addHeader(TOKEN_KEY, token)
        return chain.proceed(originalRequest.build())
    }
}