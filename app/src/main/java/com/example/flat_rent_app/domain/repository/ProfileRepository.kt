package com.example.flat_rent_app.domain.repository

import com.example.flat_rent_app.domain.model.AuthUser
import com.example.flat_rent_app.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    //Наблюдатель за профилем
    fun observerProfile(): Flow<UserProfile?>

    //Создать/обновить мой профиль
    suspend fun upsertMyProfile(profile: UserProfile): Result<Unit>

    //Получить часть профилей для отображения на главном экране
    suspend fun getFeedProfiles(limit: Int = 30): Result<List<UserProfile>>
}