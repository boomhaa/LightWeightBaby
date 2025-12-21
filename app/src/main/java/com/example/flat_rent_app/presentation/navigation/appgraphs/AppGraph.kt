package com.example.flat_rent_app.presentation.navigation.appgraphs

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flat_rent_app.presentation.navigation.Routes
import com.example.flat_rent_app.presentation.screens.mainscreen.MainScreen
import com.example.flat_rent_app.presentation.screens.profilescreen.ProfileScreen
import com.example.flat_rent_app.presentation.screens.questionnairescreen.QuestionnaireScreen

@Composable
fun AppGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.homeScreen.route
    ) {
        composable(Routes.homeScreen.route) {
            MainScreen(
                onGoProfile = { navController.navigate(Routes.profileScreen.route){

                } },
                onGoQuestionnaire = { navController.navigate(Routes.formScreen.route) }
            )
        }

        composable(Routes.profileScreen.route) {
            ProfileScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.formScreen.route) {
            QuestionnaireScreen(onBack = { navController.popBackStack() })
        }
    }
}
