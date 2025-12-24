package com.example.flat_rent_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flat_rent_app.domain.model.SwipeProfile
import com.example.flat_rent_app.domain.repository.ProfileRepository
import com.example.flat_rent_app.domain.repository.SwipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val swipeRepository: SwipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    init {
        loadProfiles()
    }

    fun loadProfiles() {
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                // Задержка 2 секунды (как в задании)
                delay(2000L)

                // Получаем реальные профили из репозитория
                val result = profileRepository.getFeedProfiles(limit = 10)

                result.onSuccess { userProfiles ->
                    // Преобразуем UserProfile в SwipeProfile
                    val swipeProfiles = userProfiles.map { userProfile ->
                        SwipeProfile(
                            uid = userProfile.uid,
                            name = extractName(userProfile.name),
                            age = extractAgeFromDescription(userProfile.description),
                            city = userProfile.city,
                            university = userProfile.eduPlace,
                            description = userProfile.description,
                            lookingFor = extractLookingFor(userProfile.description),
                            photoUrl = userProfile.photoSlots.getOrNull(0)?.fullUrl
                        )
                    }

                    _state.update { it.copy(
                        profiles = swipeProfiles,
                        isLoading = false,
                        currentIndex = if (swipeProfiles.isNotEmpty()) 0 else -1
                    ) }
                }.onFailure { error ->
                    _state.update { it.copy(
                        isLoading = false,
                        error = "Ошибка загрузки: ${error.message}"
                    ) }
                }

            } catch (e: Exception) {
                _state.update { it.copy(
                    isLoading = false,
                    error = "Ошибка: ${e.message}"
                ) }
            }
        }
    }

    fun swipeRight() {
        val currentIndex = _state.value.currentIndex
        val profiles = _state.value.profiles

        if (currentIndex in profiles.indices) {
            val targetId = profiles[currentIndex].uid

            viewModelScope.launch {
                // Отправляем лайк через существующий репозиторий
                swipeRepository.likeUser(targetId)
                    .onSuccess { outcome ->
                        // outcome может быть LikedOnly или Match
                        println("Лайк отправлен: $outcome")
                    }
                    .onFailure { error ->
                        println("Ошибка лайка: ${error.message}")
                    }

                // Переключаем на следующего пользователя
                moveToNext()
            }
        }
    }

    fun swipeLeft() {
        val currentIndex = _state.value.currentIndex
        val profiles = _state.value.profiles

        if (currentIndex in profiles.indices) {
            val targetId = profiles[currentIndex].uid

            viewModelScope.launch {
                // Отправляем пас через существующий репозиторий
                swipeRepository.passUser(targetId)
                    .onSuccess {
                        println("Пас отправлен")
                    }
                    .onFailure { error ->
                        println("Ошибка паса: ${error.message}")
                    }

                // Переключаем на следующего пользователя
                moveToNext()
            }
        }
    }

    private fun moveToNext() {
        val nextIndex = _state.value.currentIndex + 1
        val profiles = _state.value.profiles

        _state.update { state ->
            if (nextIndex < profiles.size) {
                state.copy(currentIndex = nextIndex)
            } else {
                // Все просмотрены, начинаем сначала или показываем пустое состояние
                state.copy(
                    currentIndex = if (profiles.isNotEmpty()) 0 else -1,
                    showAllViewed = profiles.isNotEmpty()
                )
            }
        }
    }

    fun retry() {
        loadProfiles()
    }

    // Вспомогательные функции для парсинга данных
    private fun extractName(fullName: String): String {
        return fullName.split(",").firstOrNull()?.trim() ?: fullName
    }

    private fun extractAgeFromDescription(description: String): Int {
        // Простая логика для примера
        return (18..35).random()
    }

    private fun extractLookingFor(description: String): String {
        return when {
            description.contains("работ", ignoreCase = true) -> "Работаю"
            description.contains("учусь", ignoreCase = true) -> "Учусь"
            description.contains("студент", ignoreCase = true) -> "Студент"
            else -> "Ищу соседа"
        }
    }
}

data class MainScreenState(
    val profiles: List<SwipeProfile> = emptyList(),
    val currentIndex: Int = -1,
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAllViewed: Boolean = false
)