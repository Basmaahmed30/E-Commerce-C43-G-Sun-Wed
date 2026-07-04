package com.route.e_commercec43gsunwed.screens.products

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.route.domain.model.Result
import com.route.domain.model.products.ProductItem
import com.route.e_commercec43gsunwed.LocalNavController
import com.route.e_commercec43gsunwed.destinations.AppRoutes
import com.route.e_commercec43gsunwed.utils.ECommerceSearchAppBar
import com.route.e_commercec43gsunwed.utils.ProductCard
import kotlinx.coroutines.flow.collect

@Composable
fun ProductsScreen(modifier: Modifier = Modifier, subCategoryId: String?) {
    val viewModel: ProductsViewModel = hiltViewModel()
    val state = viewModel.states.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        Log.e("TAG Sub", "ProductsScreen: Sub Category Id = $subCategoryId ")
        viewModel.handleActions(ProductsContract.Actions.GetProducts(subCategoryId))
    }
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ProductsContract.Events.AddToCartEvent -> {
                    Toast.makeText(context, "${event.product?.title} added to cart", Toast.LENGTH_SHORT).show()
                }
                is ProductsContract.Events.AddToWishlistEvent -> {
                    Toast.makeText(context, "${event.product?.title} added to wishlist", Toast.LENGTH_SHORT).show()
                }
                ProductsContract.Events.Idle -> {}
                ProductsContract.Events.NavigateToCart -> {}
                is ProductsContract.Events.NavigateToProductDetails -> {
                    navController.navigate(AppRoutes.ProductDetailsDestination(event.product?.id))
                }
            }
        }
    }
    ProductsContent(
        modifier = modifier,
        productsState = state.value.products,
        onAction = { action -> viewModel.handleActions(action) }
    )
}

@Composable
fun ProductsContent(
    modifier: Modifier = Modifier,
    productsState: Result<List<ProductItem>>?,
    onAction: (ProductsContract.Actions) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Scaffold(modifier = modifier, containerColor = colorScheme.onSecondary) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ECommerceSearchAppBar(
                modifier = Modifier,
                onCartClick = { onAction(ProductsContract.Actions.ClickedOnCart) },
                onSearchClick = {})
            when (productsState) {
                is Result.Error -> {

                }

                is Result.Success -> {
                    val products = productsState.data ?: emptyList()
                    ProductsLazyGrid(
                        products = products,
                        onProductClick = { product -> onAction(ProductsContract.Actions.ClickedOnProduct(product)) },
                        onAddCartClick = { product -> onAction(ProductsContract.Actions.ClickedAddToCart(product)) },
                        onAddWishlistClick = { product -> onAction(ProductsContract.Actions.ClickedAddToWishlist(product)) }
                    )
                }
                is Result.Loading<*> -> {}
                null -> {}
            }
        }
    }
}

@Composable
fun ProductsLazyGrid(
    modifier: Modifier = Modifier,
    products: List<ProductItem>,
    onProductClick: (ProductItem) -> Unit,
    onAddCartClick: (ProductItem) -> Unit,
    onAddWishlistClick: (ProductItem) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(products.size) { index ->
            val product = products[index]
            ProductCard(
                modifier = Modifier,
                product = product,
                onProductClick = onProductClick,
                onAddCartClick = onAddCartClick,
                onAddWishlistClick = onAddWishlistClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview() {
    MaterialTheme {
        ProductsContent(
            productsState = Result.Success(
                listOf(
                    ProductItem(title = "Product 1", price = 100),
                    ProductItem(title = "Product 2", price = 200),
                    ProductItem(title = "Product 3", price = 300),
                    ProductItem(title = "Product 4", price = 400)
                )
            ),
            onAction = {}
        )
    }
}
