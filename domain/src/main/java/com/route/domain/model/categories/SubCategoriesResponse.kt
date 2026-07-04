package com.route.domain.model.categories

data class SubCategoriesResponse(
	val metadata: SubCategoryMetadata? = null,
	val data: List<SubCategoryItem>? = null,
	val results: Int? = null
)

data class SubCategoryItem(
	val createdAt: String? = null,
	val name: String? = null,
	val id: String? = null,
	val category: String? = null,
	val slug: String? = null,
	val updatedAt: String? = null,
    val image: String? = null
)

data class SubCategoryMetadata(
	val numberOfPages: Int? = null,
	val limit: Int? = null,
	val currentPage: Int? = null
)

