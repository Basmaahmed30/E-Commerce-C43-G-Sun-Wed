package com.route.domain.usecases.category

import com.route.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository,
) {
    suspend fun invoke() = repository.getCategories()
}