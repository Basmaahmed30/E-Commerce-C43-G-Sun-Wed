package com.route.data.dataSource.api.auth

import com.route.data.model.auth.AuthResponseDM
import com.route.data.model.auth.request.LoginRequestParamsDM
import com.route.data.model.auth.request.RegistrationRequestParamsDM
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signin")
    suspend fun login(
        @Body request: LoginRequestParamsDM
    ): Response<AuthResponseDM>

    @POST("auth/signup")
    suspend fun register(
        @Body request: RegistrationRequestParamsDM
    ): Response<AuthResponseDM>
}
