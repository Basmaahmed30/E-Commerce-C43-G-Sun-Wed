package com.route.e_commercec43gsunwed.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.route.e_commercec43gsunwed.R

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    hint: String? = null,
    label: String? = null,
    error: String? = null,
    isPassword: Boolean = false,
) {
    var text by remember { mutableStateOf("") }
    val colorScheme = MaterialTheme.colorScheme
    Column(
        modifier = modifier.fillMaxWidth(0.9F),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label ?: "",
            color = colorScheme.onSecondary,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.Start),
            fontWeight = FontWeight.W500,
            fontSize = 18.sp
        )
        TextField(
            value = text,
            onValueChange = {
                text = it
                onTextChanged(it)
            },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth(), isError = error?.isNotEmpty() == true,
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            placeholder = {
                Text(
                    hint ?: "",
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp,
                    color = colorScheme.onTertiary,
                )
            },
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = colorScheme.onTertiary,
                errorPlaceholderColor = colorScheme.onTertiary,
                unfocusedPlaceholderColor = colorScheme.onTertiary,
                focusedTextColor = colorScheme.tertiary,
                errorTextColor = colorScheme.tertiary,
                unfocusedTextColor = colorScheme.tertiary,
                errorContainerColor = colorScheme.onSecondary,
                focusedContainerColor = colorScheme.onSecondary,
                unfocusedContainerColor = colorScheme.onSecondary
            )
        )
        if (error?.isNotEmpty() == true)
            Text(
                text = error, color = Color.Red, fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start),
            )
        else
            Spacer(Modifier.height(20.dp))

    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    hint: String? = null,
    onClick: () -> Unit,
) {
    var text by remember { mutableStateOf("") }
    val colorScheme = MaterialTheme.colorScheme

    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        shape = RoundedCornerShape(25.dp),
        modifier = modifier
            .clickable(true) {
                onClick()
            }
            .border(width = 1.dp, color = colorScheme.secondary, shape = RoundedCornerShape(25.dp)),
        enabled = false,
        singleLine = true,
        prefix = {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(
                    R.string.icon_search
                )
            )
        },
        placeholder = {
            Text(
                hint ?: "",
                fontWeight = FontWeight.Light,
                fontSize = 18.sp,
                color = colorScheme.onTertiary,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = colorScheme.onPrimary,
            errorPlaceholderColor = colorScheme.onPrimary,
            unfocusedPlaceholderColor = colorScheme.onPrimary,
            disabledPlaceholderColor = colorScheme.onPrimary,

            focusedTextColor = colorScheme.tertiary,
            errorTextColor = colorScheme.tertiary,
            unfocusedTextColor = colorScheme.tertiary,
            disabledTextColor = colorScheme.tertiary,
            errorContainerColor = colorScheme.onSecondary,
            focusedContainerColor = colorScheme.onSecondary,
            unfocusedContainerColor = colorScheme.onSecondary,
            disabledContainerColor = colorScheme.onSecondary,
            disabledPrefixColor = colorScheme.secondary,
            disabledIndicatorColor = Color.Transparent,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun AuthTextFieldPreview() {
    MaterialTheme {
        AuthTextField(
            onTextChanged = {},
            label = "Label",
            hint = "Hint text"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    MaterialTheme {
        SearchTextField(
            hint = "Search for products",
            onClick = {}
        )
    }
}
