package com.example.flat_rent_app.domain.repository

import com.example.flat_rent_app.domain.model.AuthUser
import com.example.flat_rent_app.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun observerProfile(): Flow<UserProfile?>

    suspend fun upsertMyProfile(profile: UserProfile): Result<Unit>

}