package com.route.e_commercec43gsunwed.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.route.e_commercec43gsunwed.R

@Composable
fun ECommerceSearchAppBar(
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    Column(modifier.padding(horizontal = 16.dp)) {
        Image(
            painter = painterResource(R.drawable.e_commerce_route_logo_blue),
            contentDescription = stringResource(R.string.app_logo)
        )
        Row(
            modifier = Modifier
                .padding(top = 18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Search Bar
            SearchTextField(
                modifier = Modifier

                    .weight(1F),
                hint = stringResource(R.string.what_do_you_search_for),
            ) {
                onSearchClick()
            }
            Image(
                painter = painterResource(R.drawable.ic_cart),
                contentDescription = stringResource(R.string.icon_of_the_cart),
                modifier = Modifier
                    .padding(start = 26.dp)
                    .clickable(true) {
                        onCartClick()
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchAppBarPreview() {
    ECommerceSearchAppBar(onSearchClick = {}, onCartClick = {})
}

