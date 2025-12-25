package com.example.flat_rent_app.domain.model

import android.net.Uri

data class OnboardingState(
    val name: String = "",
    val city: String = "",
    val eduPlace: String = "",
    val description: String = "",
    val pickedPhotoUri: Uri? = null,
    val uploadedPhoto: ProfilePhoto? = null,
    val loading: Boolean = false,
    val saved: Boolean = false,
    val error: String? = null,
    val preferences: Set<String> = emptySet(),
)