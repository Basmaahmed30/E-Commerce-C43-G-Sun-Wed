package com.route.data.repository.products

import com.route.domain.model.Result
import com.route.domain.model.products.ProductDetailsData
import com.route.domain.model.products.ProductItem
import com.route.domain.repository.ProductsRemoteDataSource
import com.route.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductsRemoteDataSource
) : ProductsRepository {
    override suspend fun getProducts(subCategory: String?): Flow<Result<List<ProductItem>>> =
        remoteDataSource.fetchProducts(subCategory)

    override suspend fun getProductDetails(productId: String?): Flow<Result<ProductDetailsData?>> =
        remoteDataSource.fetchProductDetails(productId)


}
