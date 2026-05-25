package com.route.e_commercec43gsunwed.utils

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.route.e_commercec43gsunwed.R

@Composable
fun ErrorDialog(modifier: Modifier = Modifier, errorState: String?, onDismissRequest: () -> Unit) {
    val colorScheme = MaterialTheme.colorScheme
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Text(
                stringResource(R.string.ok),
                color = colorScheme.secondary,
                modifier = Modifier.clickable(true) {
                    onDismissRequest()
                }
            )
        },
        text = {
            Text(errorState ?: "", color = colorScheme.tertiary)
        },
        containerColor = colorScheme.onSecondary,
        titleContentColor = colorScheme.secondary,
        textContentColor = colorScheme.secondary,
    )
}
