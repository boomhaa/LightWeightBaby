package com.example.flat_rent_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flat_rent_app.domain.model.AuthUser
import com.example.flat_rent_app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    authRepo: AuthRepository
) : ViewModel() {

    val user: StateFlow<AuthUser?> = authRepo.currentUser
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}