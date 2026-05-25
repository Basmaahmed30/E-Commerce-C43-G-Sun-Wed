package com.route.e_commercec43gsunwed.screens.splash

sealed interface SplashDirections {
    data object Login : SplashDirections
    data object Home : SplashDirections
}
