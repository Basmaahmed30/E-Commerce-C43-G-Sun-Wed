package com.route.domain.usecases.category

import com.route.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetSubCategoriesUseCase @Inject constructor(private val repository: CategoriesRepository) {
    suspend fun invoke(categoryId: String) = repository.getSubcategories(categoryId)
}