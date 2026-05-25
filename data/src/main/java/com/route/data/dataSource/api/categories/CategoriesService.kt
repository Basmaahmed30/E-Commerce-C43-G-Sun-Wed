package com.route.data.dataSource.api.categories

import com.route.data.model.categories.CategoriesResponseDM
import com.route.data.model.categories.SubCategoriesResponseDM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesService {
    @GET("categories")
    suspend fun fetchCategories(): Response<CategoriesResponseDM>

    @GET("categories/{category}/subcategories")
    suspend fun fetchSubCategories(@Path("category") category: String): Response<SubCategoriesResponseDM>
}
