package com.route.data.dataSource.api.products

import com.route.data.model.products.ProductDetailsResponseDM
import com.route.data.model.products.ProductsResponseDM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {
    @GET("products")
    suspend fun fetchProducts(@Query("category[in]") subCategoryId: String?): Response<ProductsResponseDM>

    @GET("products/{product_id}")
    suspend fun fetchProductDetails(@Path("product_id") productId: String?): Response<ProductDetailsResponseDM>
}
