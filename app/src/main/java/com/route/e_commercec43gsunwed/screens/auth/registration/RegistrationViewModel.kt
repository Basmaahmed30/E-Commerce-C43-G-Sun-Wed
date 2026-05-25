package com.route.e_commercec43gsunwed.screens.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.domain.model.Result
import com.route.domain.model.auth.AuthResponse
import com.route.domain.model.auth.request.RegistrationRequestParams
import com.route.domain.usecases.auth.RegistrationUseCase
import com.route.e_commercec43gsunwed.utils.regex.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {
    val registerState = MutableStateFlow<Result<AuthResponse>?>(null)
    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    val nameErrorState = MutableStateFlow<RegistrationValidator>(RegistrationValidator.Idle)
    val emailErrorState = MutableStateFlow<RegistrationValidator>(RegistrationValidator.Idle)
    val passwordErrorState = MutableStateFlow<RegistrationValidator>(RegistrationValidator.Idle)
    val phoneErrorState = MutableStateFlow<RegistrationValidator>(RegistrationValidator.Idle)
    val isLoading = MutableStateFlow(false)
    private fun validateFields(): Boolean {
        val name = name.value
        val phoneNumber = phoneNumber.value
        val email = email.value
        val password = password.value
        val egyptianNumberRegex = "^01[0-25]\\d{8}$".toRegex()
        if (name.isEmpty() || name.isBlank()) {
            nameErrorState.value = RegistrationValidator.Empty
            return false
        } else
            nameErrorState.value = RegistrationValidator.Idle
        if (name.length < 3) {
            nameErrorState.value = RegistrationValidator.Short
            return false
        } else
            nameErrorState.value = RegistrationValidator.Idle

        if (!name.all {
                it.isLetter() || it.isWhitespace()
            }) {
            nameErrorState.value = RegistrationValidator.Invalid
            return false
        } else
            nameErrorState.value = RegistrationValidator.Idle

        if (phoneNumber.isEmpty() || phoneNumber.isBlank()) {
            phoneErrorState.value = RegistrationValidator.Empty
            return false
        } else
            phoneErrorState.value = RegistrationValidator.Idle
        if (phoneNumber.length < 11) {
            phoneErrorState.value = RegistrationValidator.Short
            return false
        } else
            phoneErrorState.value = RegistrationValidator.Idle
        if (!egyptianNumberRegex.matches(phoneNumber)) {
            phoneErrorState.value = RegistrationValidator.Invalid
            return false
        } else {
            phoneErrorState.value = RegistrationValidator.Idle
        }
        if (email.isEmpty() || email.isBlank()) {
            emailErrorState.value = RegistrationValidator.Empty
            return false
        } else
            emailErrorState.value = RegistrationValidator.Idle
        if (!email.isValidEmail()) {
            emailErrorState.value = RegistrationValidator.Invalid
            return false
        } else
            emailErrorState.value = RegistrationValidator.Idle
        if (password.isEmpty() || password.isBlank()) {
            passwordErrorState.value = RegistrationValidator.Empty
            return false
        } else
            passwordErrorState.value = RegistrationValidator.Idle
        if (password.length < 6) {
            passwordErrorState.value = RegistrationValidator.Short
            return false
        } else
            passwordErrorState.value = RegistrationValidator.Idle
        return true
    }

    fun updateName(name: String) {
        _name.value = name
    }

    fun updatePhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun updateEmailAddress(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun register() {
        if (validateFields()) {
            viewModelScope.launch {
                isLoading.value = true
                registrationUseCase.invoke(
                    RegistrationRequestParams(
                        password = password.value,
                        phone = phoneNumber.value,
                        rePassword = password.value,
                        name = name.value,
                        email = email.value
                    )
                ).collect {
                    isLoading.value = false
                    registerState.value = it
                }
            }
        }

    }
    fun resetState() {
        registerState.value = null
    }
}
