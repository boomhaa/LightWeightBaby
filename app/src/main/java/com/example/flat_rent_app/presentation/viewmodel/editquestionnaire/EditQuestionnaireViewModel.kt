package com.example.flat_rent_app.presentation.viewmodel.editquestionnaire

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flat_rent_app.domain.repository.AuthRepository
import com.example.flat_rent_app.domain.repository.EditQuestionnaireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditQuestionnaireViewModel @Inject constructor(
    private val editRepo: EditQuestionnaireRepository,
    private val authRepo: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EditQuestionnaireState())
    val state: StateFlow<EditQuestionnaireState> = _state

    private val currentUserFlow = authRepo.currentUser

    fun onNameChanged(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun onCityChanged(city: String) {
        _state.update { it.copy(city = city) }
    }

    fun onEduPlaceChanged(eduPlace: String) {
        _state.update { it.copy(eduPlace = eduPlace) }
    }

    fun onDescriptionChanged(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun saveQuestionnaire() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val currentUser = currentUserFlow.first()
                val userId = currentUser?.uid ?: throw Exception("Пользователь не авторизован")

                val existingQuestionnaire = editRepo.getQuestionnaire(userId)

                val questionnaire = com.example.flat_rent_app.domain.model.EditQuestionnaire(
                    uid = userId,
                    name = state.value.name,
                    city = state.value.city,
                    eduPlace = state.value.eduPlace,
                    description = state.value.description,
                    mainPhotoIndex = state.value.mainPhotoIndex,
                    photoSlots = state.value.photoSlots,
                    createdAtMillis = existingQuestionnaire?.createdAtMillis ?: System.currentTimeMillis(),
                    updatedAtMillis = System.currentTimeMillis()
                )

                val result = editRepo.saveQuestionnaire(questionnaire)

                result.onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            error = null
                        )
                    }
                }.onFailure { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Ошибка сохранения"
                        )
                    }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Ошибка"
                    )
                }
            }
        }
    }

    fun loadQuestionnaire() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                val currentUser = currentUserFlow.first()
                val userId = currentUser?.uid ?: return@launch

                val questionnaire = editRepo.getQuestionnaire(userId)

                questionnaire?.let { q ->
                    _state.update {
                        it.copy(
                            name = q.name,
                            city = q.city,
                            eduPlace = q.eduPlace,
                            description = q.description,
                            mainPhotoIndex = q.mainPhotoIndex,
                            photoSlots = q.photoSlots,
                            createdAtMillis = q.createdAtMillis,
                            isLoading = false
                        )
                    }
                } ?: run {
                    val emailName = currentUser.email?.substringBefore("@") ?: ""
                    _state.update {
                        it.copy(
                            name = emailName,
                            isLoading = false
                        )
                    }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Не удалось загрузить анкету"
                    )
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    init {
        loadQuestionnaire()
    }
}