package com.route.data.di.products

import com.route.data.dataSource.api.products.ProductsService
import com.route.data.dataSource.products.ProductsRemoteDataSourceImpl
import com.route.data.repository.products.ProductsRepositoryImpl
import com.route.domain.repository.ProductsRemoteDataSource
import com.route.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {
    @Provides
    @Singleton
    fun provideProductsService(retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductRemoteDataSource(service: ProductsService): ProductsRemoteDataSource {
        return ProductsRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideProductRepository(remoteDataSource: ProductsRemoteDataSource): ProductsRepository {
        return ProductsRepositoryImpl(remoteDataSource)
    }
}
