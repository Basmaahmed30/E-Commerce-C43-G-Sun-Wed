package com.route.e_commercec43gsunwed.screens.auth.login

sealed interface LoginValidator {
    data object Idle : LoginValidator
    data object Empty : LoginValidator
    data object Invalid : LoginValidator
    data object Short : LoginValidator
}

