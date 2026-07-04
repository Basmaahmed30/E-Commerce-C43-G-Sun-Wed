package com.route.e_commercec43gsunwed.screens.home.composable.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.domain.model.Result
import com.route.domain.usecases.auth.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel(), AccountContract.ViewModel {

    private val _states = MutableStateFlow(AccountContract.States())
    override val states: StateFlow<AccountContract.States> = _states

    private val _events = MutableSharedFlow<AccountContract.Events>()
    override val events: SharedFlow<AccountContract.Events> = _events.asSharedFlow()

    override fun handleAction(actions: AccountContract.Actions) {
        when (actions) {
            AccountContract.Actions.LoadAccountInfo -> {
                viewModelScope.launch {
                    getUserUseCase.invoke().collect { result ->
                        if (result is Result.Success) {
                            _states.value = _states.value.copy(
                                userName = result.data?.first?.name ?: "User",
                                userEmail = result.data?.first?.email ?: "",
                                userPassword = result.data?.second ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}
