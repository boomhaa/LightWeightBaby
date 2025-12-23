package com.example.flat_rent_app.presentation.screens.onboarding

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.flat_rent_app.presentation.viewmodel.onboarding.OnboardingViewModel

@Composable
fun OnbPhotoScreen(
    onBack: () -> Unit,
    onNext: () -> Unit,
    viewModel: OnboardingViewModel
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.onPickedPhoto(uri)
    }

    val canGoNext = state.uploadedPhoto != null

    Scaffold { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Профиль: шаг 2/4", style = MaterialTheme.typography.headlineMedium)

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Text("Выбрать фото из галереи")
            }

            if (state.pickedPhotoUri != null || state.uploadedPhoto != null) {
                val model = state.uploadedPhoto?.fullUrl ?: state.pickedPhotoUri

                AsyncImage(
                    model = model,
                    contentDescription = "Главное фото",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading && state.pickedPhotoUri != null,
                onClick = { viewModel.uploadMainPhoto(context) }
            ) {
                if (state.loading) {
                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    Spacer(Modifier.height(8.dp))
                }
                Text("Загрузить как главное фото")
            }
            state.uploadedPhoto?.let { p ->
                Text("Загружено ✅", style = MaterialTheme.typography.bodyMedium)
            }

            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Spacer(Modifier.height(4.dp))

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
