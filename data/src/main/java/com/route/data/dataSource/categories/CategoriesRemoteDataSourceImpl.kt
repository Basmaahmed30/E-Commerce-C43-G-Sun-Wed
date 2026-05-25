package com.route.data.dataSource.categories

import com.route.data.dataSource.api.categories.CategoriesService
import com.route.data.dataSource.utils.safeApiCall
import com.route.data.mapper.categories.toDomainCategory
import com.route.data.mapper.categories.toDomainSubCategory
import com.route.domain.model.Result
import com.route.domain.model.categories.CategoryItem
import com.route.domain.model.categories.SubCategoryItem
import com.route.domain.repository.CategoriesRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRemoteDataSourceImpl @Inject constructor(
    private val service: CategoriesService
) : CategoriesRemoteDataSource {
    override suspend fun fetchCategories(): Flow<Result<List<CategoryItem>>> =
        safeApiCall({ service.fetchCategories() }, mapper = {
            it.data?.map {
                it.toDomainCategory()
            } ?: listOf()
        })

    override suspend fun fetchSubcategories(categoryId: String): Flow<Result<List<SubCategoryItem>>> =
        safeApiCall(apiCall = {
            service.fetchSubCategories(categoryId)
        }, mapper = {
            it.data?.map {
                it.toDomainSubCategory()
            } ?: listOf()
        })

}
