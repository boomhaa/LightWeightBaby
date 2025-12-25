package com.example.flat_rent_app.presentation.navigation.appgraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flat_rent_app.presentation.navigation.Routes
import com.example.flat_rent_app.presentation.screens.chatscreen.ChatScreen
import com.example.flat_rent_app.presentation.screens.chatsscreen.ChatsScreen
import com.example.flat_rent_app.presentation.screens.mainscreen.MainScreen
import com.example.flat_rent_app.presentation.screens.profilescreen.ProfileScreen
import com.example.flat_rent_app.presentation.screens.questionnairescreen.QuestionnaireScreen
import com.example.flat_rent_app.presentation.screens.editquestionnairescreen.EditQuestionnaireScreen

@Composable
fun AppGraph() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.homeScreen.route
    ) {
        composable(Routes.homeScreen.route) {
            MainScreen(
                onGoProfile = { navController.navigate(Routes.profileScreen.route) },
                onGoChats = { navController.navigate(Routes.ChatsScreen.route) },
            )
        }

        composable(Routes.profileScreen.route) {
            ProfileScreen(
                onGoHome = { navController.navigate(Routes.homeScreen.route) },
                onGoChats = { navController.navigate(Routes.ChatsScreen.route) },
                onEditQuestionnaire = { navController.navigate(Routes.EditQuestionnaire.route) })
        }

        composable(Routes.formScreen.route) {
            QuestionnaireScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.EditQuestionnaire.route) {
            EditQuestionnaireScreen(
                onBack = { navController.popBackStack() },
                onSaveComplete = { navController.popBackStack() }
            )
        }

        composable(Routes.ChatsScreen.route) {
            ChatsScreen(onOpenChat = { chatId, otherUid ->
                navController.navigate(Routes.ChatScreen.create(chatId, otherUid))
            },
                onGoHome = { navController.navigate(Routes.homeScreen.route) },
                onGoProfile = { navController.navigate(Routes.profileScreen.route) },)
        }

        composable(
            route = Routes.ChatScreen.route,
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType },
                navArgument("otherUid") { type = NavType.StringType }
            )
        ) {
            ChatScreen(onBack = { navController.popBackStack() })
        }
    }
}
