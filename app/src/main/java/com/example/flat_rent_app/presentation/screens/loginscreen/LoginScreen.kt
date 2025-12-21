package com.example.flat_rent_app.presentation.screens.loginscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.flat_rent_app.presentation.viewmodel.authviewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onGoRegister: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Авторизация", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.email,
                onValueChange = viewModel::onEmail,
                label = { Text("Email") },
                singleLine = true
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.password,
                onValueChange = viewModel::onPassword,
                label = { Text("Пароль") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true
            )

            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
                onClick = viewModel ::login
            ) {
                if (state.loading) {
                    CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.width(8.dp))
                }
                Text("Войти")
            }

            TextButton(onClick = onGoRegister) {
                Text("Нет аккаунта? Регистрация")
            }
        }
    }
}
