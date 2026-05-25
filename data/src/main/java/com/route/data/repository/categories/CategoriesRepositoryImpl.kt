package com.route.data.repository.categories

import com.route.domain.model.Result
import com.route.domain.model.categories.CategoryItem
import com.route.domain.model.categories.SubCategoryItem
import com.route.domain.repository.CategoriesRemoteDataSource
import com.route.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoriesRemoteDataSource
) : CategoriesRepository {
    override suspend fun getCategories(): Flow<Result<List<CategoryItem>>> =
        remoteDataSource.fetchCategories()

    override suspend fun getSubcategories(categoryId: String): Flow<Result<List<SubCategoryItem>>> =
        remoteDataSource.fetchSubcategories(categoryId)

}
