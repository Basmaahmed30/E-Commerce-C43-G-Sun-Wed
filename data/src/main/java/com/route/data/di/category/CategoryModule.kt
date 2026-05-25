package com.route.data.di.category

import com.route.data.dataSource.api.categories.CategoriesService
import com.route.data.dataSource.categories.CategoriesRemoteDataSourceImpl
import com.route.data.repository.categories.CategoriesRepositoryImpl
import com.route.domain.repository.CategoriesRemoteDataSource
import com.route.domain.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryModule {
    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit): CategoriesService {
        return retrofit.create(CategoriesService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoryRemoteDataSource(service: CategoriesService): CategoriesRemoteDataSource {
        return CategoriesRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        remoteDataSource: CategoriesRemoteDataSource
    ): CategoriesRepository {
        return CategoriesRepositoryImpl(remoteDataSource)
    }

}