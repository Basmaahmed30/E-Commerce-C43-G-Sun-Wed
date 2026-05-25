package com.route.e_commercec43gsunwed.screens.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.route.domain.model.Result
import com.route.e_commercec43gsunwed.LocalNavController
import com.route.e_commercec43gsunwed.R
import com.route.e_commercec43gsunwed.destinations.AppRoutes
import com.route.e_commercec43gsunwed.utils.AuthButton
import com.route.e_commercec43gsunwed.utils.AuthTextField
import com.route.e_commercec43gsunwed.utils.ErrorDialog

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state = viewModel.loginState.collectAsStateWithLifecycle()
    val isLoading = viewModel.isLoading.collectAsStateWithLifecycle()
    val emailErrorState = viewModel.emailAddressErrorState.collectAsStateWithLifecycle()
    val passwordErrorState = viewModel.passwordErrorState.collectAsStateWithLifecycle()
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    val colorScheme = MaterialTheme.colorScheme
    val navController = LocalNavController.current
    emailError = when (emailErrorState.value) {
        LoginValidator.Empty -> stringResource(R.string.e_mail_address_required)
        LoginValidator.Idle -> ""

        LoginValidator.Invalid -> stringResource(R.string.e_mail_address_invalid)
        LoginValidator.Short -> ""
    }
    passwordError = when (passwordErrorState.value) {
        LoginValidator.Empty -> stringResource(R.string.password_required)
        LoginValidator.Short -> stringResource(R.string.short_password)
        LoginValidator.Idle -> ""
        LoginValidator.Invalid -> ""
    }
    LaunchedEffect(Unit) {
        viewModel.loginDirections.collect {
            when (it) {
                LoginDirections.Home -> {
                    navController.navigate(AppRoutes.HomeDestination) {
                        popUpTo(AppRoutes.LoginDestination) {
                            inclusive = true
                            saveState = true
                        }
                    }
                }

                LoginDirections.Registration -> {
                    navController.navigate(AppRoutes.RegistrationDestination)
                }
            }
        }
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
        Text(
            text = stringResource(R.string.welcome_back_to_route),
            modifier = Modifier
                .padding(top = 80.dp, start = 16.dp)
                .align(
                    Alignment.Start
                ),
            color = colorScheme.onSecondary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        )
        Text(
            text = stringResource(R.string.please_sign_in_with_your_mail),
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp)
                .align(
                    Alignment.Start
                ),
            color = colorScheme.onSecondary,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp,
        )
        AuthTextField(
            modifier = Modifier.padding(top = 40.dp),
            onTextChanged = {
                viewModel.updateEmailAddress(it)
            },
            hint = stringResource(R.string.enter_your_email_address),
            label = stringResource(R.string.e_mail_address),
            error = emailError,
        )
        AuthTextField(
            modifier = Modifier.padding(top = 32.dp),
            onTextChanged = {
                viewModel.updatePassword(it)
            },
            hint = stringResource(R.string.enter_your_password),
            label = stringResource(R.string.password),
            error = passwordError,
        )
        AuthButton(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.9F),
            text = stringResource(R.string.login),
            isLoading = isLoading.value
        ) { viewModel.login() }
        Text(
            text = stringResource(R.string.don_t_have_an_account_create_account),
            modifier = Modifier
                .padding(top = 32.dp)
                .clickable(true) {
                    viewModel.navigateToRegister()
                },
            fontWeight = FontWeight.W500,
            fontSize = 18.sp
        )
    }
    val loginState = state.value
    when (loginState) {
        is Result.Error -> {
            ErrorDialog(errorState = loginState.failure.message) {
                viewModel.resetState()
            }

        }

        is Result.Success -> {

        }

        null -> {}
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen()
}
