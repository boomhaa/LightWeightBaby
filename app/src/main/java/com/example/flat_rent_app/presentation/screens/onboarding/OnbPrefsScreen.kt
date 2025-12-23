package com.example.flat_rent_app.presentation.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flat_rent_app.presentation.viewmodel.onboarding.OnboardingViewModel

@Composable
fun OnbPrefsScreen(
    viewModel: OnboardingViewModel,
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val canGoNext = true

    val options = listOf(
        "Не курю",
        "Без животных",
        "Сова",
        "Жаворонок",
        "Только центр",
        "Тихие соседи",
        "Без вечеринок"
    )

    Scaffold { pad ->
        Column(
            Modifier.padding(pad).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Профиль: шаг 3/4", style = MaterialTheme.typography.headlineMedium)

            options.forEach { pref ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = pref in state.preferences,
                        onCheckedChange = { viewModel.togglePreference(pref) }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(pref)
                }
            }

            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Spacer(Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = canGoNext,
                onClick = onNext
            ) {
                Text("Далее")
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