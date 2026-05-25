package com.route.data.dataSource.products

import com.route.data.dataSource.api.products.ProductsService
import com.route.data.dataSource.utils.safeApiCall
import com.route.data.mapper.products.toDomainProduct
import com.route.data.mapper.products.toDomainProductDetails
import com.route.domain.model.Result
import com.route.domain.model.products.ProductDetailsData
import com.route.domain.model.products.ProductItem
import com.route.domain.repository.ProductsRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val service: ProductsService
) : ProductsRemoteDataSource {
    override suspend fun fetchProducts(subCategory: String?): Flow<Result<List<ProductItem>>> =
        safeApiCall(apiCall = {
            service.fetchProducts(subCategory)
        }, mapper = {
            it.data?.map {
                it.toDomainProduct()
            } ?: listOf()
        })

    override suspend fun fetchProductDetails(productId: String?): Flow<Result<ProductDetailsData?>> =
        safeApiCall(apiCall = {
            service.fetchProductDetails(productId)
        }, mapper = {
            it.productDetailsDM?.toDomainProductDetails()
        })
}
