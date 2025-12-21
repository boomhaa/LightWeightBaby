package com.example.flat_rent_app.presentation.screens.profilescreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.flat_rent_app.presentation.viewmodel.profileviewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.user.collectAsState()

    Scaffold { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Профиль (заглушка)", style = MaterialTheme.typography.headlineMedium)

            Text("UID: ${user?.uid ?: "-"}")
            Text("Email: ${user?.email ?: "-"}")

            Button(onClick = viewModel::signOut, modifier = Modifier.fillMaxWidth()) {
                Text("Выйти")
            }

            OutlinedButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Назад")
            }
        }
    }
}
