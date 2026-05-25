package com.route.domain.repository

import com.route.domain.model.Result
import com.route.domain.model.categories.CategoryItem
import com.route.domain.model.categories.SubCategoryItem
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun getCategories(): Flow<Result<List<CategoryItem>>>
    suspend fun getSubcategories(categoryId: String): Flow<Result<List<SubCategoryItem>>>
}

interface CategoriesRemoteDataSource {
    suspend fun fetchCategories(): Flow<Result<List<CategoryItem>>>
    suspend fun fetchSubcategories(categoryId: String): Flow<Result<List<SubCategoryItem>>>
}
