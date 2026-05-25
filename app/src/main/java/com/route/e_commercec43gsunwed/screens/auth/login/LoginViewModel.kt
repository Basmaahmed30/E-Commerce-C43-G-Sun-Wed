package com.route.e_commercec43gsunwed.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.domain.model.Result
import com.route.domain.model.auth.AuthResponse
import com.route.domain.model.auth.request.LoginRequestParams
import com.route.domain.usecases.auth.LoginUseCase
import com.route.e_commercec43gsunwed.utils.regex.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    // StateFlow   vs    LiveData
    // Kotlin Coroutines
    //                                  0
    //    1
    val loginState = MutableStateFlow<Result<AuthResponse>?>(null)
    val emailAddressErrorState = MutableStateFlow<LoginValidator>(LoginValidator.Idle)
    val emailAddress = MutableStateFlow("")
    val password = MutableStateFlow("")
    val passwordErrorState = MutableStateFlow<LoginValidator>(LoginValidator.Idle)
    val isLoading = MutableStateFlow(false)
    private val _loginDirections = MutableSharedFlow<LoginDirections>()
    val loginDirections = _loginDirections.asSharedFlow()

    fun resetState() {
        loginState.value = null
    }

    private fun validateFields(): Boolean {
        val email = emailAddress.value
        val password = password.value
        if (email.isEmpty() || email.isBlank()) {
            emailAddressErrorState.value = LoginValidator.Empty
            return false
        } else
            emailAddressErrorState.value = LoginValidator.Idle
        if (!email.isValidEmail()) {
            emailAddressErrorState.value = LoginValidator.Invalid
            return false
        } else
            emailAddressErrorState.value = LoginValidator.Idle
        if (password.isEmpty() || password.isBlank()) {
            passwordErrorState.value = LoginValidator.Empty
            return false
        } else
            passwordErrorState.value = LoginValidator.Idle
        if (password.length < 6) {
            passwordErrorState.value = LoginValidator.Short
            return false
        } else
            passwordErrorState.value = LoginValidator.Idle
        return true
    }


    fun updateEmailAddress(emailAddress: String) {
        this.emailAddress.value = emailAddress
    }

    fun updatePassword(password: String) {
        this.password.value = password
    }

    fun login() {
        if (validateFields()) {
            viewModelScope.launch {
                isLoading.value = true
                loginUseCase.invoke(
                    LoginRequestParams(
                        password = password.value,
                        email = emailAddress.value
                    )
                ).collect {
                    loginState.value = it
                    isLoading.value = false
                    if (it is Result.Success) {
                        _loginDirections.emit(LoginDirections.Home)
                    }
                }

            }
        }
    }

    fun navigateToRegister() {
        viewModelScope.launch {
            _loginDirections.emit(LoginDirections.Registration)
        }
    }

}