package com.example.flat_rent_app.domain.model

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val city: String = "",
    val eduPlace: String = "",
    val description: String = "",
    val mainPhotoIndex: Int = 0,
    val photoSlots: List<ProfilePhoto?> = listOf(null, null, null),
    val createdAtMillis: Long? = null,
    val updatedAtMillis: Long? = null
)