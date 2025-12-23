package com.example.flat_rent_app.presentation.viewmodel.profileviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flat_rent_app.domain.model.AuthUser
import com.example.flat_rent_app.domain.repository.AuthRepository
import com.example.flat_rent_app.domain.repository.EditQuestionnaireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val questionnaireRepo: EditQuestionnaireRepository
) : ViewModel() {

    val user: StateFlow<AuthUser?> = authRepo.currentUser
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName.asStateFlow()

    fun signOut() {
        viewModelScope.launch { authRepo.signOut() }
    }

    fun loadUserName() {
        viewModelScope.launch {
            val currentUser = user.value
            if (currentUser != null) {
                val questionnaire = questionnaireRepo.getQuestionnaire(currentUser.uid)
                _userName.value = questionnaire?.name
            }
        }
    }

    init {
        user.onEach { user ->
            if (user != null) {
                val questionnaire = questionnaireRepo.getQuestionnaire(user.uid)
                _userName.value = questionnaire?.name
            } else {
                _userName.value = null
            }
        }.launchIn(viewModelScope)
    }
}