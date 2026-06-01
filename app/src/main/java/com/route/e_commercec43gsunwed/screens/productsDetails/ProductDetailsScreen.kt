package com.route.e_commercec43gsunwed.screens.productsDetails

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.route.domain.model.Result
import com.route.domain.model.products.ProductDetailsData
import com.route.domain.model.products.ProductItem
import com.route.e_commercec43gsunwed.R
import com.route.e_commercec43gsunwed.utils.ProductDetailsToolbar
import com.route.e_commercec43gsunwed.utils.pager.ProductDetailImagesPager

@Composable
fun ProductDetailsScreen(modifier: Modifier = Modifier, productItemId: String?) {
    Log.e("TAG", "ProductDetailsScreen: $productItemId")
    val viewModel: ProductDetailsViewModel = hiltViewModel()
    val states = viewModel.states.collectAsStateWithLifecycle()
    val colorScheme = MaterialTheme.colorScheme
    LaunchedEffect(Unit) {
        viewModel.handleActions(ProductDetailsContract.Actions.GetProductDetails(productItemId))
    }
    Scaffold(modifier = modifier, containerColor = colorScheme.onSecondary) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ProductDetailsToolbar(onSearchClick = {
                viewModel.handleActions(ProductDetailsContract.Actions.ClickedOnSearch)
            }, onBackClick = {
                viewModel.handleActions(ProductDetailsContract.Actions.ClickedOnBack)
            }, onCartClick = {
                viewModel.handleActions(ProductDetailsContract.Actions.ClickedOnCart)
            })
            val productDetailsState = states.value.productDetails
            when (productDetailsState) {
                is Result.Error -> {
                    Log.e(
                        "TAG Error",
                        "ProductDetailsScreen: ${productDetailsState.failure.message}",
                    )
                }

                is Result.Success -> {
                    ProductDetailImagesPager(modifier = Modifier, productDetailsState.data)
                    ProductNamePriceRow(modifier = Modifier, productDetailsState.data)
                    ProductRatingCartRow(modifier = Modifier, productDetailsState.data)
                    ProductDescriptionColumn(modifier = Modifier, productDetailsState.data)
                }

                null -> {}
            }
        }

    }
}

@Composable
fun ProductDescriptionColumn(modifier: Modifier = Modifier, productDetails: ProductDetailsData?) {
    val colorScheme = MaterialTheme.colorScheme
    if (productDetails?.description != null)
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.description),
                color = colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.W500
            )
            Text(
                text = productDetails.description ?: "",
                color = colorScheme.onPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400
            )
        }
}

@Composable
fun ProductRatingCartRow(modifier: Modifier = Modifier, productDetails: ProductDetailsData?) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (productDetails?.sold != null)
            Text(
                "${productDetails.sold} Sold", modifier = Modifier
                    .border(
                        1.dp, colorScheme.secondary,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(
                        vertical = 8.dp, horizontal = 16.dp
                    )
            )
        Spacer(Modifier.size(16.dp))
        Image(painter = painterResource(R.drawable.ic_rating), contentDescription = null)
        Text(
            "${productDetails?.ratingsAverage}", color = colorScheme.tertiary,
            fontSize = 14.sp, fontWeight = FontWeight.W400
        )
        Text(
            " ( ${productDetails?.ratingsQuantity} )", color = colorScheme.tertiary,
            fontSize = 14.sp, fontWeight = FontWeight.W400
        )
        // Cart + -
        Spacer(Modifier.weight(1F))
        ProductCartActions()
    }
}

@Composable
fun ProductCartActions(modifier: Modifier = Modifier) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = modifier
            .background(colorScheme.secondary, RoundedCornerShape(20.dp))
            .padding(vertical = 11.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_minus),
            contentDescription = stringResource(R.string.icon_decrement_product_from_cart),
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            "0",
            color = colorScheme.onSecondary,
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 22.dp)
        )
        Image(
            painter = painterResource(R.drawable.ic_plus),
            contentDescription = stringResource(R.string.icon_increment_product_into_cart),
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}

@Composable
fun ProductNamePriceRow(modifier: Modifier = Modifier, productDetails: ProductDetailsData?) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = productDetails?.title ?: "",
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            color = colorScheme.secondary,
            modifier = Modifier.fillMaxWidth(0.4F)
        )
        Spacer(Modifier.weight(1F))
        Text(
            text = "${productDetails?.price}",
            fontWeight = FontWeight.W500,
            fontSize = 18.sp,
            color = colorScheme.secondary,
        )
        Text(
            text = "EGP", fontSize = 18.sp,
            fontWeight = FontWeight.W500, color = colorScheme.secondary
        )
    }
}
