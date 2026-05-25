package com.route.e_commercec43gsunwed.screens.auth.login

sealed interface LoginDirections {
    data object Registration : LoginDirections
    data object Home : LoginDirections
}
