package com.route.domain.usecases.products

import com.route.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend fun invoke(subCategory: String? = null) =
        repository.getProducts(subCategory = subCategory)
}
