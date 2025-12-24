package com.example.flat_rent_app.presentation.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.runtime.remember
import com.example.flat_rent_app.domain.model.SwipeProfile
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flat_rent_app.presentation.viewmodel.MainViewModel
import com.example.flat_rent_app.presentation.screens.profiledetailscreen.ProfileDetailScreen

// Моковые данные для теста
val mockProfiles = listOf(
    SwipeProfile(
        uid = "1",
        name = "Антон",
        age = 23,
        city = "Москва, Россия",
        university = "МГТУ им. Н. Э. Баумана",
        description = "Тишина",
        lookingFor = "Работаю",
        photoUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=800&auto=format&fit=crop"
    ),
    SwipeProfile(
        uid = "2",
        name = "Мария",
        age = 24,
        city = "Санкт-Петербург, Россия",
        university = "СПбГУ",
        description = "Люблю готовить",
        lookingFor = "Учусь",
        photoUrl = "https://images.unsplash.com/photo-1494790108755-2616b612b786?w=800&auto=format&fit=crop"
    ),
    SwipeProfile(
        uid = "3",
        name = "Иван",
        age = 25,
        city = "Казань, Россия",
        university = "КФУ",
        description = "Спокойный",
        lookingFor = "Работаю удалённо",
        photoUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=800&auto=format&fit=crop"
    )
)


@Composable
fun SwipeableProfileCard(
    profile: SwipeProfile,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    modifier: Modifier = Modifier
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y

                        // Если свайпнули достаточно далеко по горизонтали
                        if (abs(offsetX) > 300f) {
                            if (offsetX > 0) {
                                onSwipeRight()
                            } else {
                                onSwipeLeft()
                            }
                            offsetX = 0f
                            offsetY = 0f
                        }
                    },
                    onDragEnd = {
                        // Возвращаем карточку на место если не дотянули
                        offsetX = 0f
                        offsetY = 0f
                    }
                )
            }
            .offset(x = offsetX.dp, y = offsetY.dp)
    ) {
        ProfileCard(
            name = profile.name,
            age = profile.age,
            city = profile.city,
            university = profile.university,
            description = profile.description,
            lookingFor = profile.lookingFor,
            photoUrl = profile.photoUrl
        )

        // Индикатор направления свайпа
        if (abs(offsetX) > 50f) {
            val color = if (offsetX > 0) Color.Green else Color.Red
            val icon = if (offsetX > 0) Icons.Default.Favorite else Icons.Default.Close
            val text = if (offsetX > 0) "LIKE" else "NOPE"

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        icon,
                        contentDescription = text,
                        tint = color,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        text = text,
                        color = color,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileCard(
    name: String,
    age: Int,
    city: String,
    university: String,
    description: String,
    lookingFor: String,
    photoUrl: String? = null  // ← новый параметр для фото
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Фоновая картинка
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = "Фото профиля",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Если нет фото - градиентный фон
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF6A11CB), // Фиолетовый сверху
                                    Color(0xFF2575FC)  // Синий снизу
                                )
                            )
                        )
                )
            }

            // Полупрозрачный чёрный слой сверху для лучшей читаемости текста
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
            )

            // Текст поверх фото
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Имя и возраст - вверху
                Text(
                    text = "$name, $age",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Информация - внизу
                Column {
                    // Город с иконкой
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Город",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = city,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Университет с иконкой
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.School,
                            contentDescription = "Университет",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = university,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Описание в белой плашке
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.9f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = description,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = lookingFor,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun AllViewedView(onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp)
    ) {
        Text("Все профили просмотрены", style = MaterialTheme.typography.headlineSmall)
        Text("Загляните позже или обновите список", color = Color.Gray)
        Button(onClick = onRetry) {
            Text("Обновить")
        }
    }
}

@Composable
fun MainScreen(
    onGoProfile: () -> Unit,
    onGoQuestionnaire: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    if (state.showProfileDetails) {
        val profile = state.selectedProfile
        if (profile != null) {
            ProfileDetailScreen(
                profile = profile,
                onBack = viewModel::closeProfileDetails
            )
            return
        }
    }
    Scaffold(
    ) { pad ->
        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Основной контент
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isLoading -> {
                        LoadingView()
                    }

                    state.error != null -> {
                        ErrorView(
                            error = state.error!!,
                            onRetry = viewModel::retry
                        )
                    }

                    state.profiles.isEmpty() -> {
                        EmptyView()
                    }

                    state.showAllViewed -> {
                        AllViewedView(onRetry = viewModel::retry)
                    }

                    else -> {
                        val currentProfile = state.profiles[state.currentIndex]
                        SwipeableProfileCard(
                            profile = currentProfile,
                            onSwipeLeft = viewModel::swipeLeft,
                            onSwipeRight = viewModel::swipeRight,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопки действий (только если есть данные)
            if (!state.isLoading && state.error == null && state.profiles.isNotEmpty() && !state.showAllViewed) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ExtendedFloatingActionButton(
                            onClick = viewModel::swipeLeft,
                            containerColor = Color.Red.copy(alpha = 0.1f),
                            contentColor = Color.Red,
                            icon = { Icon(Icons.Default.Close, "Пас") },
                            text = { Text("Пас") }
                        )

                        ExtendedFloatingActionButton(
                            onClick = {
                                viewModel.openProfileDetails()
                            },
                            containerColor = Color.Blue.copy(alpha = 0.1f),
                            contentColor = Color.Blue,
                            icon = { Icon(Icons.Default.Info, "Инфо") },
                            text = { Text("Инфо") }
                        )

                        ExtendedFloatingActionButton(
                            onClick = viewModel::swipeRight,
                            containerColor = Color.Green.copy(alpha = 0.1f),
                            contentColor = Color.Green,
                            icon = { Icon(Icons.Default.Favorite, "Лайк") },
                            text = { Text("Лайк") }
                        )
                    }

                    // Счетчик профилей
                    Text(
                        text = "${state.currentIndex + 1}/${state.profiles.size}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Кнопки навигации
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onGoProfile,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Перейти в профиль")
                }

                Button(
                    onClick = onGoQuestionnaire,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Анкета (заглушка)")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun LoadingView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgressIndicator()
        Text("Загрузка профилей...", color = Color.Gray)
    }
}

@Composable
fun ErrorView(error: String, onRetry: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp)
    ) {
        Text("Ошибка", style = MaterialTheme.typography.headlineSmall)
        Text(error, color = Color.Red)
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}

@Composable
fun EmptyView() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Нет доступных профилей", color = Color.Gray)
        Text("Попробуйте позже", color = Color.Gray, fontSize = 14.sp)
    }
}