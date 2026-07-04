package com.route.e_commercec43gsunwed.screens.home.composable.categories

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.collect
import com.route.domain.model.Result
import com.route.domain.model.categories.CategoryItem
import com.route.domain.model.categories.SubCategoryItem
import com.route.e_commercec43gsunwed.LocalNavController
import com.route.e_commercec43gsunwed.destinations.AppRoutes
import com.route.e_commercec43gsunwed.utils.ECommerceSearchAppBar

@Composable
fun CategoriesTab(modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<CategoriesViewModel>()
    val state = viewModel.states.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    val context = LocalContext.current
    var selectedCategory by remember { mutableStateOf<CategoryItem?>(null) }
    
    LaunchedEffect(Unit) {
        viewModel.handleActions(CategoriesContract.Action.GetCategories)
    }
    
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                CategoriesContract.Events.Idle -> {}
                CategoriesContract.Events.NavigateToCart -> {}
                is CategoriesContract.Events.NavigateToProducts -> {
                    navController.navigate(
                        AppRoutes.ProductsDestination(
                            event.subCategoryId
                        )
                    )
                }
                CategoriesContract.Events.NavigateToSearch -> {}
                is CategoriesContract.Events.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    CategoriesContent(
        modifier = modifier,
        state = state.value,
        selectedCategory = selectedCategory,
        onAction = { action ->
            if (action is CategoriesContract.Action.SelectCategory) {
                selectedCategory = action.category
            }
            viewModel.handleActions(action)
        }
    )
}

@Composable
fun CategoriesContent(
    modifier: Modifier = Modifier,
    state: CategoriesContract.States,
    selectedCategory: CategoryItem?,
    onAction: (CategoriesContract.Action) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Column(modifier = modifier.fillMaxSize()) {
        ECommerceSearchAppBar(modifier = Modifier, onCartClick = {
            onAction(CategoriesContract.Action.ClickedOnCart)
        }, onSearchClick = {
            onAction(CategoriesContract.Action.ClickedOnSearch)
        })
        Row(modifier = Modifier.fillMaxWidth()) {
            // 1- Categories
            when (val categoriesState = state.categoriesList) {
                is Result.Error -> {}
                is Result.Success -> {
                    CategoriesTabsLazyColumn(
                        modifier = Modifier.fillMaxWidth(0.34F),
                        categoriesList = categoriesState.data ?: listOf(),
                        selectedCategory = selectedCategory,
                        onCategorySelected = {
                            onAction(CategoriesContract.Action.SelectCategory(it))
                        }
                    )
                }
                is Result.Loading<*> -> {}
                null -> {
                    CircularProgressIndicator(color = colorScheme.secondary)
                }
            }
            
            // 2- Sub-Categories
            when (val subCategoriesState = state.subCategoriesList) {
                is Result.Error -> {}
                is Result.Success -> {
                    SubCategoryLazyVerticalGrid(
                        modifier = Modifier,
                        subCategories = subCategoriesState.data ?: listOf(),
                        headerImage = selectedCategory?.image,
                        headerTitle = selectedCategory?.name
                    ) {
                        onAction(CategoriesContract.Action.SelectSubCategory(it))
                    }
                }
                is Result.Loading<*> -> {}
                null -> {}
            }
        }
    }
}

@Composable
fun CategoriesTabsLazyColumn(
    modifier: Modifier = Modifier,
    categoriesList: List<CategoryItem>,
    selectedCategory: CategoryItem?,
    onCategorySelected: (CategoryItem) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    LazyColumn(
        modifier = modifier
            .padding(start = 16.dp, top = 16.dp)
            .background(
                color = colorScheme.secondaryContainer,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        items(categoriesList.size) { index ->
            val category = categoriesList[index]
            CategoryTab(
                modifier = Modifier,
                category = category,
                onCategorySelected = onCategorySelected,
                isSelected = selectedCategory == category
            )
        }
    }
}

@Composable
fun SubCategoryLazyVerticalGrid(
    modifier: Modifier = Modifier,
    subCategories: List<SubCategoryItem>,
    headerImage: String? = null,
    headerTitle: String? = null,
    onSubCategorySelected: (SubCategoryItem) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (headerTitle != null) {
            item(span = { GridItemSpan(3) }) {
                Text(
                    text = headerTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        if (headerImage != null) {
            item(span = { GridItemSpan(3) }) {
                AsyncImage(
                    model = headerImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable { 
                            // Navigate to all products in this category
                            onSubCategorySelected(SubCategoryItem(id = headerTitle, name = headerTitle))
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
        items(subCategories.size) { index ->
            SubCategoryCard(
                modifier = Modifier,
                subCategory = subCategories[index],
                onSubCategorySelected = onSubCategorySelected
            )
        }
    }
}

@Composable
fun SubCategoryCard(
    modifier: Modifier = Modifier,
    subCategory: SubCategoryItem,
    onSubCategorySelected: (SubCategoryItem) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .width(80.dp)
            .clickable { onSubCategorySelected(subCategory) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = subCategory.image,
            contentDescription = subCategory.name,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(colorScheme.secondaryContainer),
            contentScale = ContentScale.Crop
        )
        Text(
            text = subCategory.name ?: "",
            fontSize = 12.sp,
            fontWeight = FontWeight.W400,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 4.dp),
            color = colorScheme.onBackground
        )
    }
}

@Composable
fun CategoryTab(
    modifier: Modifier = Modifier,
    category: CategoryItem,
    isSelected: Boolean = false,
    onCategorySelected: (CategoryItem) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(color = if (isSelected) colorScheme.onSecondary else colorScheme.secondaryContainer)
            .clickable {
                if (!isSelected)
                    onCategorySelected(category)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (isSelected)
            Box(
                Modifier
                    .padding(horizontal = 4.dp)
                    .height(70.dp)
                    .width(6.dp)
                    .background(colorScheme.secondary)
            )
        Text(
            text = category.name ?: "", 
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            color = if (isSelected) colorScheme.secondary else colorScheme.onBackground,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryTabPreview1() {
    CategoryTab(category = CategoryItem(name = "Men's Fashion"), isSelected = true) { }
}

@Preview(showBackground = true)
@Composable
private fun CategoryTabPreview2() {
    CategoryTab(category = CategoryItem(name = "Women's Fashion"), isSelected = false) { }
}

@Preview(showBackground = true)
@Composable
private fun CategoriesTabsLazyColumnPreview() {
    val list = listOf(
        CategoryItem(name = "Men's Fashion"),
        CategoryItem(name = "Women's Fashion"),
        CategoryItem(name = "Electronics"),
        CategoryItem(name = "Beauty")
    )
    CategoriesTabsLazyColumn(
        modifier = Modifier.width(120.dp),
        categoriesList = list,
        selectedCategory = list[1],
        onCategorySelected = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun CategoriesTabPreview() {
    val categoryList = listOf(
        CategoryItem(name = "Men's Fashion", id = "1"),
        CategoryItem(name = "Women's Fashion", id = "2"),
        CategoryItem(name = "Electronics", id = "3")
    )
    val subCategoryList = listOf(
        SubCategoryItem(name = "T-shirts", image = "https://loremflickr.com/320/240/tshirt,men"),
        SubCategoryItem(name = "Shorts", image = "https://loremflickr.com/320/240/shorts,men"),
        SubCategoryItem(name = "Jeans", image = "https://loremflickr.com/320/240/jeans")
    )
    MaterialTheme {
        CategoriesContent(
            state = CategoriesContract.States(
                categoriesList = Result.Success(categoryList),
                subCategoriesList = Result.Success(subCategoryList)
            ),
            selectedCategory = categoryList[0],
            onAction = { _ -> }
        )
    }
}
