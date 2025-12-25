package com.example.flat_rent_app.presentation.navigation

sealed class Routes(val route: String) {
    object authScreen : Routes("auth")
    object regScreen : Routes("registration")
    object homeScreen : Routes("home")
    object profileScreen : Routes("profile")
    object formScreen : Routes("form")
    object EditQuestionnaire : Routes("editquestionnaire")
    object welcomeScreen : Routes("welcome")
}