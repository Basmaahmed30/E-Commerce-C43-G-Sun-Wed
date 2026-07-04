package com.route.e_commercec43gsunwed.screens.home.composable.account

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AccountContract {
    interface ViewModel {
        fun handleAction(actions: Actions)
        val states: StateFlow<States>
        val events: SharedFlow<Events>
    }

    sealed interface Actions {
        data object LoadAccountInfo : Actions
    }

    sealed interface Events {
        data object Idle : Events
    }

    data class States(
        val userName: String = "User",
        val userEmail: String = "",
        val userPhone: String = "",
        val userAddress: String = "",
        val userPassword: String = ""
    )
}
