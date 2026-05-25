package com.route.data.dataSource.auth.remote

import com.route.data.dataSource.api.auth.AuthService
import com.route.data.dataSource.utils.safeApiCall
import com.route.data.mapper.auth.toDataModel
import com.route.data.mapper.auth.toDomainEntity
import com.route.domain.model.Result
import com.route.domain.model.auth.AuthResponse
import com.route.domain.model.auth.request.LoginRequestParams
import com.route.domain.model.auth.request.RegistrationRequestParams
import com.route.domain.repository.AuthRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val service: AuthService
) : AuthRemoteDataSource {
    override suspend fun login(params: LoginRequestParams): Flow<Result<AuthResponse>> {
        return safeApiCall(apiCall = {
            service.login(params.toDataModel())
        }, mapper = {
            it.toDomainEntity()
        })


    }

    override suspend fun register(params: RegistrationRequestParams): Flow<Result<AuthResponse>> {
        return safeApiCall(apiCall = {
            service.register(params.toDataModel())
        }, mapper = {
            it.toDomainEntity()
        })
    }


}