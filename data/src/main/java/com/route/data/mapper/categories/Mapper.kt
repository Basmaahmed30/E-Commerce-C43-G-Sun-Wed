package com.route.data.mapper.categories

import com.route.data.model.categories.CategoryDM
import com.route.data.model.categories.SubCategoryItemDM
import com.route.domain.model.categories.CategoryItem
import com.route.domain.model.categories.SubCategoryItem

fun CategoryDM.toDomainCategory(): CategoryItem {
    return CategoryItem(
        image = image,
        createdAt = createdAt,
        name = name,
        id = id,
        slug = slug,
        updatedAt = updatedAt
    )
}

fun SubCategoryItemDM.toDomainSubCategory(): SubCategoryItem =
    SubCategoryItem(
        createdAt = createdAt, name = name, id = id,
        category = category, slug = slug, updatedAt = updatedAt
    )

