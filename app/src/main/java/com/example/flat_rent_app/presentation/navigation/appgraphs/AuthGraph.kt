package com.example.flat_rent_app.presentation.navigation.appgraphs


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flat_rent_app.presentation.navigation.Routes
import com.example.flat_rent_app.presentation.screens.loginscreen.LoginScreen
import com.example.flat_rent_app.presentation.screens.regscreen.RegisterScreen

@Composable
fun AuthGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.authScreen.route
    ) {
        composable(Routes.authScreen.route) {
            LoginScreen(
                onGoRegister = { navController.navigate(Routes.regScreen.route) }
            )
        }

        composable(Routes.regScreen.route) {
            RegisterScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
