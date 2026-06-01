package com.route.domain.model.products

data class ProductDetailsResponse(
    val productDetailsData: ProductDetailsData? = null
)

data class ProductDetailsBrand(
    val image: String? = null,
    val name: String? = null,
    val id: String? = null,
    val slug: String? = null
)

data class ProductDetailsData(
    val sold: Int? = null,
    val images: List<String>? = null,
    val quantity: Int? = null,
    val imageCover: String? = null,
    val description: String? = null,
    val title: String? = null,
    val ratingsQuantity: Int? = null,
    val ratingsAverage: Double? = null,
    val createdAt: String? = null,
    val price: Int? = null,
    val v: Int? = null,
    val id: String? = null,
    val brand: ProductDetailsBrand? = null,
    val slug: String? = null,
    val updatedAt: String? = null
)
