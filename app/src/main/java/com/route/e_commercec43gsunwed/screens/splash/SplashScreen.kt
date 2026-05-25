package com.route.e_commercec43gsunwed.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.route.e_commercec43gsunwed.LocalNavController
import com.route.e_commercec43gsunwed.R
import com.route.e_commercec43gsunwed.destinations.AppRoutes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    val colorScheme = MaterialTheme.colorScheme
    val viewModel: SplashViewModel = hiltViewModel()
    val navController = LocalNavController.current
    LaunchedEffect(Unit) {
        delay(1_400)
        viewModel.navigate()
    }
    LaunchedEffect(Unit) {
        viewModel.state.collect {
            when (it) {
                SplashDirections.Home -> {
                    navController.navigate(AppRoutes.HomeDestination) {
                        popUpTo(AppRoutes.SplashDestination) {
                            inclusive = true
                            saveState = true
                        }
                    }
                }

                SplashDirections.Login -> {
                    navController.navigate(AppRoutes.LoginDestination) {
                        popUpTo(AppRoutes.SplashDestination) {
                            inclusive = true
                            saveState = true
                        }
                    }
                }
                // Composable Function A
                // Composable Function B

            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorScheme.secondary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(R.drawable.splash_blur_effect),
            contentDescription = null,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(R.drawable.e_commerce_logo_route),
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier
                .weight(1F)
                .padding(36.dp),
            contentScale = ContentScale.Inside
        )
        Image(
            painter = painterResource(R.drawable.splash_bottom_blur_effect),
            contentDescription = null,
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .fillMaxHeight(0.24F),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
