package com.route.data.mapper.products

import com.route.data.model.products.ProductBrandDM
import com.route.data.model.products.ProductDM
import com.route.data.model.products.ProductDetailsBrandDM
import com.route.data.model.products.ProductDetailsDM
import com.route.domain.model.products.ProductDetailsBrand
import com.route.domain.model.products.ProductDetailsData
import com.route.domain.model.products.ProductItem

fun ProductDM.toDomainProduct(): ProductItem {
    return ProductItem(
        sold = sold,
        images = images,
        quantity = quantity,
        imageCover = imageCover,
        description = description,
        title = title,
        ratingsQuantity = ratingsQuantity,
        ratingsAverage = ratingsAverage,
        createdAt = createdAt,
        price = price,
        id = id,
        brand = productBrandDM?.toDomainBrand(),
        updatedAt = updatedAt,
        priceAfterDiscount = priceAfterDiscount
    )
}

fun ProductBrandDM.toDomainBrand(): ProductDetailsBrand {
    return ProductDetailsBrand(
        image = image, name = name, id = id, slug = slug
    )
}

fun ProductDetailsDM.toDomainProductDetails(): ProductDetailsData = ProductDetailsData(
    sold = sold,
    images = images,
    quantity = quantity,
    imageCover = imageCover,
    description = description,
    title = title, ratingsQuantity = ratingsQuantity,
    ratingsAverage = ratingsAverage,
    createdAt = createdAt, price = price,
    id = id, slug = slug, updatedAt = updatedAt,
    brand = productDetailsBrandDM?.toDomainBrand()
)

fun ProductDetailsBrandDM.toDomainBrand(): ProductDetailsBrand = ProductDetailsBrand(
    image = image, name = name, id = id, slug = slug
)
