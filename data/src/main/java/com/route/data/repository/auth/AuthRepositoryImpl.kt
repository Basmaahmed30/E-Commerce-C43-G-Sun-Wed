package com.route.data.repository.auth

import android.util.Log
import com.route.domain.model.Result
import com.route.domain.model.auth.AuthResponse
import com.route.domain.model.auth.AuthUser
import com.route.domain.model.auth.request.LoginRequestParams
import com.route.domain.model.auth.request.RegistrationRequestParams
import com.route.domain.repository.AuthLocalDataSource
import com.route.domain.repository.AuthRemoteDataSource
import com.route.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
) : AuthRepository {
    override suspend fun login(params: LoginRequestParams): Flow<Result<AuthResponse>> {
        val result = remoteDataSource.login(params)
        result.collect {
            if (it is Result.Success) {
                saveToken(it.data?.token ?: "").collect {
                    when (it) {
                        is Result.Error -> {
                            Log.e("TAG", "login: Error : ${it.failure.message}")
                        }

                        is Result.Success -> {
                            Log.e("TAG", "login: Success !")
                        }
                        is Result.Loading<*> -> {}
                    }
                }
                it.data?.authUser?.let { user ->
                    saveUser(user, params.password).collect { }
                }
            }
        }
        return result
    }

    override suspend fun register(params: RegistrationRequestParams): Flow<Result<AuthResponse>> {
        val result = remoteDataSource.register(params)
        result.collect {
            if (it is Result.Success) {
                saveToken(it.data?.token ?: "").collect {
                    when (it) {
                        is Result.Error -> {
                            Log.e("TAG", "Register: Error : ${it.failure.message}")
                        }

                        is Result.Success -> {
                            Log.e("TAG", "Register: Success !")
                        }
                        is Result.Loading<*> -> {}
                    }
                }
                it.data?.authUser?.let { user ->
                    saveUser(user, params.password).collect { }
                }
            }
        }
        return result
    }

    override suspend fun saveToken(params: String): Flow<Result<Unit>> {
        return localDataSource.saveToken(params)
    }

    override suspend fun getToken(): Flow<Result<String>> {
        return localDataSource.getToken()
    }

    override suspend fun saveUser(user: AuthUser, password: String?): Flow<Result<Unit>> {
        return localDataSource.saveUser(user, password)
    }

    override suspend fun getUser(): Flow<Result<Pair<AuthUser, String>>> {
        return localDataSource.getUser()
    }


}
