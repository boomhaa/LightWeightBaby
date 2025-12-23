package com.example.flat_rent_app.presentation.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.flat_rent_app.presentation.viewmodel.onboarding.OnboardingViewModel

@Composable
fun OnbAboutScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
    viewModel: OnboardingViewModel
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
            Text("Профиль: шаг 4/4", style = MaterialTheme.typography.headlineMedium)

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.description,
                onValueChange = viewModel::onDescription,
                label = { Text("Описание о себе") },
                minLines = 4
            )

            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            if (state.saved) {
                Text("Профиль сохранён ✅", style = MaterialTheme.typography.bodyMedium)
            }

            Spacer(Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading,
                onClick = {
                    viewModel.saveProfile()
                    onFinish()
                }
            ) {
                if (state.loading) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.height(8.dp))
                }
                Text("Завершить")
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onBack
            ) {
                Text("Назад")
            }
        }
    }
}
