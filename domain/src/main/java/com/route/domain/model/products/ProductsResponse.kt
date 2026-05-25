package com.route.domain.model.products


data class ProductsResponse(
    val productsMetadata: ProductsMetadata? = null,
    val data: List<ProductItem>? = null,
    val results: Int? = null
)

data class ProductsMetadata(
    val numberOfPages: Int? = null,
    val nextPage: Int? = null,
    val limit: Int? = null,
    val currentPage: Int? = null
)

data class ProductItem(
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
    val id: String? = null,
    val subcategory: List<SubcategoryItem>? = null,
    val category: Category? = null,
    val brand: ProductDetailsBrand? = null,
    val slug: String? = null,
    val updatedAt: String? = null,
    val priceAfterDiscount: Int? = null
)

data class Brand(
    val image: String? = null,
    val name: String? = null,
    val id: String? = null,
    val slug: String? = null
)

data class Category(
    val image: String? = null,
    val name: String? = null,
    val id: String? = null,
    val slug: String? = null
)

data class SubcategoryItem(
    val name: String? = null,
    val id: String? = null,
    val category: String? = null,
    val slug: String? = null
)

