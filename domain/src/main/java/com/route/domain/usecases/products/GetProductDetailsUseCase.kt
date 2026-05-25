package com.route.domain.usecases.products

import com.route.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductDetailsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend fun invoke(productId: String?) = repository.getProductDetails(productId)
}
