package com.route.e_commercec43gsunwed.screens.productsDetails

import android.util.Log
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.route.domain.model.products.ProductItem

@Composable
fun ProductDetailsScreen(modifier: Modifier = Modifier, productItemId: String?) {
    Log.e("TAG", "ProductDetailsScreen: $productItemId")
    val viewModel: ProductDetailsViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.handleActions(ProductDetailsContract.Actions.GetProductDetails(productItemId))
    }
    Scaffold(modifier = modifier) { innerPadding ->

    }
}
