package com.route.e_commercec43gsunwed.screens.auth.registration

sealed interface RegistrationValidator {
    data object Idle : RegistrationValidator
    data object Empty : RegistrationValidator
    data object Short : RegistrationValidator
    data object Invalid : RegistrationValidator
}
