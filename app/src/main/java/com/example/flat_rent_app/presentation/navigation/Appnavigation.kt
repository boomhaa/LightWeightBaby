package com.example.flat_rent_app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.authScreen.route){
        composable(Routes.authScreen.route) {

        }

        composable(Routes.regScreen.route) {

        }

        composable(Routes.homeScreen.route) {

        }

        composable(Routes.profileScreen.route) {

        }

        composable(Routes.formScreen.route) {

        }
    }
}