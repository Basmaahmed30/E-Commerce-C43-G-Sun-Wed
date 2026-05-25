package com.route.e_commercec43gsunwed.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean = false,
    onButtonClick: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    Button(
        modifier = modifier,
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorScheme.onSecondary,
            contentColor = colorScheme.secondary
        ),
        shape = RoundedCornerShape(14.dp),
    ) {
        if (isLoading)
            CircularProgressIndicator(color = colorScheme.secondary)
        else
            Text(
                text,
                fontSize = 20.sp,
                fontWeight = FontWeight.W600,
                modifier = Modifier.padding(vertical = 10.dp)
            )
    }
}

@Preview
@Composable
private fun AuthButtonPreview() {
    AuthButton(modifier = Modifier.fillMaxWidth(), "Login") {

    }
}
