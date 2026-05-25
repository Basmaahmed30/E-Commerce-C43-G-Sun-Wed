package com.route.e_commercec43gsunwed.screens.productsDetails

import com.route.domain.model.Result
import com.route.domain.model.products.ProductDetailsData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface ProductDetailsContract {
    interface ViewModel {
        fun handleActions(action: Actions)
        val states: StateFlow<States>
        val events: SharedFlow<Events>
    }

    sealed interface Actions {
        data object Idle : Actions
        data class GetProductDetails(val productId: String?) : Actions
    }

    sealed interface Events {
        data object Idle : Events
    }

    data class States(val productDetails: Result<ProductDetailsData?>? = null)
}
