package com.route.e_commercec43gsunwed.utils.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.route.e_commercec43gsunwed.R


@Composable
fun ECommerceHorizontalPager(modifier: Modifier = Modifier) {
    val state = rememberPagerState(pageCount = { 3 })
    val colorScheme = MaterialTheme.colorScheme
    Box(modifier = modifier.height(200.dp)) {
        HorizontalPager(
            state = state, modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(200.dp)
                .fillMaxWidth()
        ) {
            for (i in 0 until state.pageCount) {
                Box(
                    Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.slider_image_1),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                    Text(
                        "Up TO 25%", modifier = Modifier.align(Alignment.CenterStart),
                        color = colorScheme.secondary
                    )
                }
            }

        }
        // Slider Dots
        Row(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 0 until state.pageCount)
                Box(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .padding(horizontal = 4.dp)
                        .size(10.dp)
                        .background(
                            color = if (state.currentPage == i) colorScheme.secondary else colorScheme.onSecondary,
                            CircleShape
                        )
                )
        }
    }
}
