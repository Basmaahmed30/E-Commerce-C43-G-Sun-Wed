package com.route.e_commercec43gsunwed.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.domain.model.Result
import com.route.domain.usecases.auth.GetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {
    private val _state =
        MutableSharedFlow<SplashDirections>() // Handle One Time Events  -> Show Toast , Dialog , navigate
    val state = _state.asSharedFlow()

    fun navigate() {
        viewModelScope.launch {
            getTokenUseCase.invoke().collect { result: Result<String> ->
                when (result) {
                    is Result.Error -> _state.emit(SplashDirections.Login)
                    is Result.Success -> {
                        if (result.data?.isEmpty() == true || result.data?.isBlank() == true) {
                            _state.emit(SplashDirections.Login)
                        } else {
                            _state.emit(SplashDirections.Home)
                        }
                    }
                    is Result.Loading<*> -> {}
                }
            }
        }
    }
}
