package com.route.e_commercec43gsunwed.screens.productsDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.domain.usecases.products.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel(), ProductDetailsContract.ViewModel {
    override fun handleActions(action: ProductDetailsContract.Actions) {
        viewModelScope.launch {
            when (action) {
                ProductDetailsContract.Actions.Idle -> {}
                is ProductDetailsContract.Actions.GetProductDetails -> {
                    getProductDetails(action.productId)
                }
            }
        }
    }

    private val _states = MutableStateFlow(ProductDetailsContract.States(null))
    override val states: StateFlow<ProductDetailsContract.States>
        get() = _states
    private val _events = MutableSharedFlow<ProductDetailsContract.Events>()
    override val events: SharedFlow<ProductDetailsContract.Events>
        get() = _events

    private fun getProductDetails(productId: String?) {
        viewModelScope.launch {
            getProductDetailsUseCase.invoke(productId).collect {
                _states.value = _states.value.copy(productDetails = it)
            }
        }
    }

}