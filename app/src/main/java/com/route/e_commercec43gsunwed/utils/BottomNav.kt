package com.route.e_commercec43gsunwed.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.route.e_commercec43gsunwed.R

@Composable
fun ECommerceBottomNav(modifier: Modifier = Modifier, onTabSelected: (Int) -> Unit) {
    var selectedIndex by remember { mutableStateOf(0) }
    val colorScheme = MaterialTheme.colorScheme
    val bottomNavItems = listOf(
        BottomNavItem(icon = R.drawable.home, stringResource(R.string.home)),
        BottomNavItem(icon = R.drawable.categories, stringResource(R.string.categories)),
        BottomNavItem(icon = R.drawable.wishlist, stringResource(R.string.wishlist)),
        BottomNavItem(icon = R.drawable.account, stringResource(R.string.account)),
    )
    BottomAppBar(
        containerColor = colorScheme.secondary,
        contentColor = colorScheme.onSecondary,
        modifier = modifier.clip(
            RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ),
        actions = {
            bottomNavItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedIndex == index,
                    onClick = {
                        selectedIndex = index
                        onTabSelected(index)
                    },
                    icon = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = if (selectedIndex == index) colorScheme.onSecondary else colorScheme.secondary,
                                    shape = CircleShape
                                )
                        ) {
                            Image(
                                painter = painterResource(item.icon),
                                contentDescription = item.title,
                                colorFilter = ColorFilter.tint(color = if (selectedIndex == index) colorScheme.secondary else colorScheme.onSecondary)
                            )
                        }
                    },
                    colors = NavigationBarItemColors(
                        selectedIconColor = colorScheme.secondary,
                        selectedTextColor = colorScheme.onSecondary,
                        selectedIndicatorColor = Color.Transparent,
                        unselectedIconColor = colorScheme.onSecondary,
                        unselectedTextColor = colorScheme.onSecondary,
                        disabledTextColor = colorScheme.secondary,
                        disabledIconColor = colorScheme.secondary
                    )
                )
            }
        })
}

@Immutable
data class BottomNavItem(val icon: Int, val title: String? = null)
