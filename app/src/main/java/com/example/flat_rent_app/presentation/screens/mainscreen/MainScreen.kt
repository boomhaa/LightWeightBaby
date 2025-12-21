package com.example.flat_rent_app.presentation.screens.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onGoProfile: () -> Unit,
    onGoQuestionnaire: () -> Unit,
) {
    Scaffold { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Главный экран (заглушка)", style = MaterialTheme.typography.headlineMedium)

            Button(onClick = onGoProfile, modifier = Modifier.fillMaxWidth()) {
                Text("Перейти в профиль")
            }

            Button(onClick = onGoQuestionnaire, modifier = Modifier.fillMaxWidth()) {
                Text("Анкета (заглушка)")
            }
        }
    }
}