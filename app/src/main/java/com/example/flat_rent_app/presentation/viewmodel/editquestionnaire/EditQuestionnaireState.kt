package com.example.flat_rent_app.presentation.viewmodel.editquestionnaire

import com.example.flat_rent_app.domain.model.ProfilePhoto

data class EditQuestionnaireState(
    val name: String = "",
    val city: String = "",
    val eduPlace: String = "",
    val description: String = "",

    val mainPhotoIndex: Int = 0,
    val photoSlots: List<ProfilePhoto?> = listOf(null, null, null),

    val createdAtMillis: Long? = null,

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)