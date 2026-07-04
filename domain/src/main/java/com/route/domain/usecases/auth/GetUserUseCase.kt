package com.route.domain.usecases.auth

import com.route.domain.model.Result
import com.route.domain.model.auth.AuthUser
import com.route.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend fun invoke(): Flow<Result<Pair<AuthUser, String>>> = repository.getUser()
}
