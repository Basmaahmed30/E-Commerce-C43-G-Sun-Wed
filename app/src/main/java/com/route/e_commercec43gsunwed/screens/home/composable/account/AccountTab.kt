package com.route.e_commercec43gsunwed.screens.home.composable.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.route.e_commercec43gsunwed.R

@Composable
fun AccountTab(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel = hiltViewModel()
) {

    val state = viewModel.states.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.handleAction(AccountContract.Actions.LoadAccountInfo)
    }

    AccountContent(
        modifier = modifier,
        state = state.value
    )
}

@Composable
fun AccountContent(
    modifier: Modifier = Modifier,
    state: AccountContract.States
) {

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.e_commerce_logo_route),
            contentDescription = null,
            modifier = Modifier.size(90.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Welcome, ${state.userName}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = colors.secondary
        )

        Text(
            text = state.userEmail,
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(28.dp))

        AccountEditItem(
            label = "Your full name",
            value = state.userName,
            hint = "Enter your name"
        )

        Spacer(modifier = Modifier.height(20.dp))

        AccountEditItem(
            label = "Your E-mail",
            value = state.userEmail,
            hint = "Enter your email"
        )

        Spacer(modifier = Modifier.height(20.dp))

        AccountEditItem(
            label = "Your password",
            value = state.userPassword,
            hint = "Password",
            isPassword = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        AccountEditItem(
            label = "Your mobile number",
            value = state.userPhone,
            hint = "Phone"
        )

        Spacer(modifier = Modifier.height(20.dp))

        AccountEditItem(
            label = "Your Address",
            value = state.userAddress,
            hint = "Address"
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun AccountEditItem(
    label: String,
    value: String,
    hint: String,
    isPassword: Boolean = false
) {

    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = colors.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            readOnly = true,
            singleLine = true,
            shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),

            visualTransformation =
                if (isPassword)
                    PasswordVisualTransformation()
                else
                    VisualTransformation.None,

            placeholder = {
                Text(
                    text = hint
                )
            },

            trailingIcon = {

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = colors.secondary
                )
            },

            colors = OutlinedTextFieldDefaults.colors(

                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,

                focusedBorderColor = Color(0xFFB6D5F7),
                unfocusedBorderColor = Color(0xFFB6D5F7),

                focusedTextColor = colors.secondary,
                unfocusedTextColor = colors.secondary,

                cursorColor = colors.secondary
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPreview() {

    MaterialTheme {

        AccountContent(

            state = AccountContract.States(

                userName = "Basma Ahmed",

                userEmail = "basma.ahmed@gmail.com",

                userPhone = "01122118855",

                userAddress = "6th October, street 11.....",

                userPassword = "password123"
            )
        )
    }
}