package com.route.e_commercec43gsunwed.screens.auth.registration

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.route.domain.model.Result
import com.route.e_commercec43gsunwed.R
import com.route.e_commercec43gsunwed.utils.AuthButton
import com.route.e_commercec43gsunwed.utils.AuthTextField
import com.route.e_commercec43gsunwed.utils.ErrorDialog

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    val state by viewModel.registerState.collectAsStateWithLifecycle()
    val nameErrorState by viewModel.nameErrorState.collectAsStateWithLifecycle()
    val phoneErrorState by viewModel.phoneErrorState.collectAsStateWithLifecycle()
    val emailErrorState by viewModel.emailErrorState.collectAsStateWithLifecycle()
    val passwordErrorState by viewModel.passwordErrorState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    RegistrationContent(
        modifier = modifier,
        state = state,
        isLoading = isLoading,
        nameErrorState = nameErrorState,
        phoneErrorState = phoneErrorState,
        emailErrorState = emailErrorState,
        passwordErrorState = passwordErrorState,
        onNameChanged = { viewModel.updateName(it) },
        onPhoneChanged = { viewModel.updatePhoneNumber(it) },
        onEmailChanged = { viewModel.updateEmailAddress(it) },
        onPasswordChanged = { viewModel.updatePassword(it) },
        onRegisterClicked = { viewModel.register() },
        onErrorDialogDismiss = { viewModel.resetState() }
    )
}

@Composable
fun RegistrationContent(
    modifier: Modifier = Modifier,
    state: Result<*>?,
    isLoading: Boolean,
    nameErrorState: RegistrationValidator,
    phoneErrorState: RegistrationValidator,
    emailErrorState: RegistrationValidator,
    passwordErrorState: RegistrationValidator,
    onNameChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRegisterClicked: () -> Unit,
    onErrorDialogDismiss: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    val nameError = when (nameErrorState) {
        RegistrationValidator.Empty -> stringResource(R.string.name_required)
        RegistrationValidator.Invalid -> stringResource(R.string.invalid_name)
        RegistrationValidator.Short -> stringResource(R.string.short_name)
        else -> ""
    }

    val phoneError = when (phoneErrorState) {
        RegistrationValidator.Empty -> stringResource(R.string.phone_required)
        RegistrationValidator.Invalid -> stringResource(R.string.invalid_phone)
        RegistrationValidator.Short -> stringResource(R.string.short_phone)
        else -> ""
    }

    val emailError = when (emailErrorState) {
        RegistrationValidator.Empty -> stringResource(R.string.e_mail_address_required)
        RegistrationValidator.Invalid -> stringResource(R.string.e_mail_address_invalid)
        else -> ""
    }

    val passwordError = when (passwordErrorState) {
        RegistrationValidator.Empty -> stringResource(R.string.password_required)
        RegistrationValidator.Short -> stringResource(R.string.short_password)
        else -> ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.e_commerce_logo_route),
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier
                .padding(top = 36.dp)
                .fillMaxHeight(0.13F),
            contentScale = ContentScale.FillHeight,
        )
        AuthTextField(
            modifier = Modifier.padding(top = 40.dp),
            onTextChanged = onNameChanged,
            label = stringResource(R.string.full_name),
            hint = stringResource(R.string.enter_your_full_name),
            error = nameError,
        )
        AuthTextField(
            modifier = Modifier.padding(top = 32.dp),
            onTextChanged = onPhoneChanged,
            label = stringResource(R.string.mobile_number),
            hint = stringResource(R.string.enter_your_mobile_no),
            error = phoneError,
        )
        AuthTextField(
            modifier = Modifier.padding(top = 32.dp),
            onTextChanged = onEmailChanged,
            label = stringResource(R.string.e_mail_address),
            hint = stringResource(R.string.enter_your_email_address),
            error = emailError,
        )
        AuthTextField(
            modifier = Modifier.padding(top = 32.dp),
            onTextChanged = onPasswordChanged,
            label = stringResource(R.string.password),
            hint = stringResource(R.string.enter_your_password),
            error = passwordError,
            isPassword = true
        )
        AuthButton(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.9F),
            text = stringResource(R.string.sign_up),
            isLoading = isLoading,
            onButtonClick = onRegisterClicked
        )
    }

    if (state is Result.Error) {
        ErrorDialog(
            errorState = state.failure.message,
            onDismissRequest = onErrorDialogDismiss
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegistrationScreenPreview() {
    MaterialTheme {
        RegistrationContent(
            state = null,
            isLoading = false,
            nameErrorState = RegistrationValidator.Idle,
            phoneErrorState = RegistrationValidator.Idle,
            emailErrorState = RegistrationValidator.Idle,
            passwordErrorState = RegistrationValidator.Idle,
            onNameChanged = {},
            onPhoneChanged = {},
            onEmailChanged = {},
            onPasswordChanged = {},
            onRegisterClicked = {},
            onErrorDialogDismiss = {}
        )
    }
}
