package com.route.domain.repository

import com.route.domain.model.Result
import com.route.domain.model.products.ProductDetailsData
import com.route.domain.model.products.ProductItem
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProducts(subCategory: String?): Flow<Result<List<ProductItem>>>
    suspend fun getProductDetails(productId: String?): Flow<Result<ProductDetailsData?>>
}

interface ProductsRemoteDataSource {
    suspend fun fetchProducts(subCategory: String?): Flow<Result<List<ProductItem>>>
    suspend fun fetchProductDetails(productId: String?): Flow<Result<ProductDetailsData?>>
}

