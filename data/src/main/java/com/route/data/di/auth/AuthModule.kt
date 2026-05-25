package com.route.data.di.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.route.data.dataSource.api.auth.AuthService
import com.route.data.dataSource.auth.local.AuthLocalDataSourceImpl
import com.route.data.dataSource.auth.remote.AuthRemoteDataSourceImpl
import com.route.data.repository.auth.AuthRepositoryImpl
import com.route.domain.repository.AuthLocalDataSource
import com.route.domain.repository.AuthRemoteDataSource
import com.route.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteDataSource: AuthRemoteDataSource,
        localDataSource: AuthLocalDataSource,
    ): AuthRepository {
        return AuthRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        service: AuthService
    ): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(dataStore: DataStore<Preferences>): AuthLocalDataSource {
        return AuthLocalDataSourceImpl(dataStore)
    }
}
