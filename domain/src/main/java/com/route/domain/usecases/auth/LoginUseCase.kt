package com.route.domain.usecases.auth

import com.route.domain.model.Result
import com.route.domain.model.auth.AuthResponse
import com.route.domain.model.auth.request.LoginRequestParams
import com.route.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(params: LoginRequestParams): Flow<Result<AuthResponse>> {
        return repository.login(params)
    }
}
