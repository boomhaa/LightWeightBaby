package com.example.flat_rent_app.presentation.navigation.appgraphs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flat_rent_app.presentation.navigation.Routes
import com.example.flat_rent_app.presentation.screens.onboarding.OnbAboutScreen
import com.example.flat_rent_app.presentation.screens.onboarding.OnbNameScreen
import com.example.flat_rent_app.presentation.screens.onboarding.OnbPhotoScreen
import com.example.flat_rent_app.presentation.screens.onboarding.OnbPrefsScreen
import com.example.flat_rent_app.presentation.viewmodel.onboarding.OnboardingViewModel

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun OnboardingGraph() {
    val navController = rememberNavController()
    val graphRoute = "onboarding_graph"

    NavHost(
        navController = navController,
        startDestination = Routes.onbNameScreen.route,
        route = graphRoute
    ) {
        composable(Routes.onbNameScreen.route) {
            val parentEntry = remember(navController, graphRoute) {
                navController.getBackStackEntry(graphRoute)
            }
            val vm: OnboardingViewModel = hiltViewModel(parentEntry)

            OnbNameScreen(
                onNext = { navController.navigate(Routes.onbPhotoScreen.route) },
                viewModel = vm
            )
        }

        composable(Routes.onbPhotoScreen.route) {
            val parentEntry = remember(navController, graphRoute) {
                navController.getBackStackEntry(graphRoute)
            }
            val vm: OnboardingViewModel = hiltViewModel(parentEntry)

            OnbPhotoScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.onbPrefsScreen.route) },
                viewModel = vm
            )
        }

        composable(Routes.onbPrefsScreen.route) {
            val parentEntry = remember(navController, graphRoute) {
                navController.getBackStackEntry(graphRoute)
            }
            val vm: OnboardingViewModel = hiltViewModel(parentEntry)

            OnbPrefsScreen(
                onBack = { navController.popBackStack() },
                onNext = { navController.navigate(Routes.onbAboutScreen.route) },
                viewModel = vm
            )
        }

        composable(Routes.onbAboutScreen.route) {
            val parentEntry = remember(navController, graphRoute) {
                navController.getBackStackEntry(graphRoute)
            }
            val vm: OnboardingViewModel = hiltViewModel(parentEntry)

            OnbAboutScreen(
                onBack = { navController.popBackStack() },
                onFinish = { },
                viewModel = vm
            )
        }
    }
}