package com.example.flat_rent_app.presentation.navigation.appgraphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flat_rent_app.presentation.navigation.Routes
import com.example.flat_rent_app.presentation.screens.onboarding.OnbAboutScreen
import com.example.flat_rent_app.presentation.screens.onboarding.OnbNameScreen
import com.example.flat_rent_app.presentation.screens.onboarding.OnbPhotoScreen

@Composable
fun OnboardingGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.onbNameScreen.route
    ) {
        composable(Routes.onbNameScreen.route) {
            OnbNameScreen(
                onNext = { navController.navigate(Routes.onbPhotoScreen.route) }
            )
        }

        composable(Routes.onbPhotoScreen.route) {
            OnbPhotoScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.onbAboutScreen.route) }
            )
        }

        composable(Routes.onbAboutScreen.route) {
            OnbAboutScreen(
                onBack = { navController.popBackStack() },
                // after saving profile RootViewModel will switch to AppGraph automatically
                onFinish = { /* no-op */ }
            )
        }
    }
}
