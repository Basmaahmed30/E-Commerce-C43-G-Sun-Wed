package com.route.domain.usecases.auth

import com.route.domain.repository.AuthRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun invoke() = repository.getToken()
}
