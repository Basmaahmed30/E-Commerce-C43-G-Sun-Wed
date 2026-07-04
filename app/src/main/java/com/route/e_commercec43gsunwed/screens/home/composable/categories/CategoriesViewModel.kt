package com.route.e_commercec43gsunwed.screens.home.composable.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.domain.model.Result
import com.route.domain.model.categories.CategoryItem
import com.route.domain.model.categories.SubCategoryItem
import com.route.domain.usecases.category.GetCategoriesUseCase
import com.route.domain.usecases.category.GetSubCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSubCategoriesUseCase: GetSubCategoriesUseCase,
) : ViewModel(), CategoriesContract.ViewModel {

    private val _states = MutableStateFlow(CategoriesContract.States())
    override val states: StateFlow<CategoriesContract.States> = _states

    private val _events = MutableSharedFlow<CategoriesContract.Events>()
    override val events: SharedFlow<CategoriesContract.Events> = _events.asSharedFlow()

    override fun handleActions(action: CategoriesContract.Action) {
        viewModelScope.launch {
            when (action) {
                CategoriesContract.Action.ClickedOnCart -> {
                    _events.emit(CategoriesContract.Events.NavigateToCart)
                }
                CategoriesContract.Action.ClickedOnSearch -> {
                    _events.emit(CategoriesContract.Events.NavigateToSearch)
                }
                CategoriesContract.Action.Idle -> {}
                is CategoriesContract.Action.SelectCategory -> {
                    val categoryName = action.category?.name ?: ""
                    if (categoryName.contains("Men", ignoreCase = true)) {
                        val mockSubCategories = listOf(
                            "T-shirts" to "https://loremflickr.com/320/240/tshirt,men",
                            "Shorts" to "https://loremflickr.com/320/240/shorts,men",
                            "Jeans" to "https://loremflickr.com/320/240/jeans",
                            "pants" to "https://loremflickr.com/320/240/pants",
                            "Footwear" to "https://loremflickr.com/320/240/shoes,men",
                            "Suits" to "https://loremflickr.com/320/240/suit",
                            "Watches" to "https://loremflickr.com/320/240/watch",
                            "Bags" to "https://loremflickr.com/320/240/bag",
                            "Eyewears" to "https://loremflickr.com/320/240/glasses"
                        ).map { SubCategoryItem(name = it.first, id = it.first, image = it.second) }
                        
                        _states.value = _states.value.copy(
                            subCategoriesList = Result.Success(mockSubCategories)
                        )
                    } else if (categoryName.contains("Women", ignoreCase = true)) {
                        val mockSubCategories = listOf(
                            "Dresses" to "https://loremflickr.com/320/240/dress,women",
                            "Jeans" to "https://loremflickr.com/320/240/jeans,women",
                            "Skirts" to "https://loremflickr.com/320/240/skirt,women",
                            "Pijamas" to "https://loremflickr.com/320/240/pijamas,women",
                            "Bags" to "https://loremflickr.com/320/240/bag,women",
                            "T-Shirt" to "https://loremflickr.com/320/240/tshirt,women",
                            "Footwear" to "https://loremflickr.com/320/240/shoes,women",
                            "Eyewear" to "https://loremflickr.com/320/240/glasses,women",
                            "Watches" to "https://loremflickr.com/320/240/watch,women"
                        ).map { SubCategoryItem(name = it.first, id = it.first, image = it.second) }

                        _states.value = _states.value.copy(
                            subCategoriesList = Result.Success(mockSubCategories)
                        )
                    } else {
                        action.category?.id?.let {
                            getSubCategories(it)
                        }
                    }
                }
                is CategoriesContract.Action.SelectSubCategory -> {
                    _events.emit(
                        CategoriesContract.Events.NavigateToProducts(
                            subCategoryId = action.subCategory?.id
                        )
                    )
                }
                CategoriesContract.Action.GetCategories -> getCategories()
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.invoke().collect { result: Result<List<CategoryItem>> ->
                when (result) {
                    is Result.Success -> {
                        val updatedList = result.data?.map { category ->
                            when {
                                category.name?.contains("Men", ignoreCase = true) == true ->
                                    category.copy(image = "https://loremflickr.com/640/360/fashion,men")
                                category.name?.contains("Women", ignoreCase = true) == true ->
                                    category.copy(image = "https://loremflickr.com/640/360/fashion,women")
                                else -> category
                            }
                        }
                        _states.value = _states.value.copy(categoriesList = Result.Success(updatedList))
                    }
                    else -> {
                        _states.value = _states.value.copy(categoriesList = result)
                    }
                }
            }
        }
    }

    private fun getSubCategories(categoryId: String) {
        viewModelScope.launch {
            getSubCategoriesUseCase.invoke(categoryId).collect {
                _states.value = _states.value.copy(subCategoriesList = it)
            }
        }
    }
}
