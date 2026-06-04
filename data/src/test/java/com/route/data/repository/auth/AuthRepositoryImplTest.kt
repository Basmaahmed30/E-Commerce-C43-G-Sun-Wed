package com.route.data.repository.auth

import com.route.domain.model.Result
import com.route.domain.model.auth.AuthResponse
import com.route.domain.model.auth.request.LoginRequestParams
import com.route.domain.repository.AuthLocalDataSource
import com.route.domain.repository.AuthRemoteDataSource
import com.route.domain.utils.Failure
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test


class AuthRepositoryImplTest {
    // 1- Target Unit   2- Context     3- Expected Result


    @Test
    fun `login() when Calling login it should call Remote Data Source login function at least once then it should called `() =
        runTest {
            // 1- Arrange
            val loginRequest = LoginRequestParams()
            val authRemoteDataSource = mockk<AuthRemoteDataSource>()
            val authLocalDataSource = mockk<AuthLocalDataSource>()
            val authRepository = AuthRepositoryImpl(authRemoteDataSource, authLocalDataSource)
            coEvery { authRemoteDataSource.login(loginRequest) } returns flow { Result.Success(null) }
            // 2- Act
            val actual = authRepository.login(loginRequest)
            // 3- Assert
            coVerify(exactly = 1) { authRemoteDataSource.login(loginRequest) }
        }

    @Test
    fun `login() with Error State in remote data source should be returning error in repository`() =
        runTest {
            // 1- Arrange
            val loginRequest = LoginRequestParams()
            val authRemoteDataSource = mockk<AuthRemoteDataSource>()
            val authLocalDataSource = mockk<AuthLocalDataSource>()
            val authRepository = AuthRepositoryImpl(authRemoteDataSource, authLocalDataSource)
            coEvery { authRemoteDataSource.login(loginRequest) } returns flow {
                emit(
                    Result.Error(
                        failure = Failure.NoDataException
                    )
                )
            }
            // 2- Act
            val actual = authRepository.login(loginRequest)
            // 3- Assert
            coVerify(exactly = 1) { authRemoteDataSource.login(loginRequest) }
            assertTrue(actual.first() is Result.Error)
        }

    @Test
    fun `login() with Success State it should call save Token then save Token should be called `() =
        runTest {
            // 1- Arrange
            val token = "123456"
            val loginRequest = LoginRequestParams()
            val authRemoteDataSource = mockk<AuthRemoteDataSource>()
            val authLocalDataSource = mockk<AuthLocalDataSource>()
            val authRepository = AuthRepositoryImpl(authRemoteDataSource, authLocalDataSource)
            coEvery { authRemoteDataSource.login(loginRequest) } returns flow {
                emit(
                    Result.Success(
                        data = AuthResponse(token = token)
                    )
                )
            }
            coEvery { authLocalDataSource.saveToken(token) } returns flow { emit(Result.Success(Unit)) }
            // 2- Act
            val actual = authRepository.login(loginRequest)
            // 3- Assert
            coVerify(exactly = 1) { authRemoteDataSource.login(loginRequest) }
            coVerify(exactly = 1) { authLocalDataSource.saveToken(token) }
            assertTrue(actual.first() is Result.Success)
        }
}