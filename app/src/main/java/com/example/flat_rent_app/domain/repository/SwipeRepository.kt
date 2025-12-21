package com.example.flat_rent_app.domain.repository

import com.example.flat_rent_app.domain.model.Match
import com.example.flat_rent_app.util.LikeOutCome
import kotlinx.coroutines.flow.Flow

interface SwipeRepository {
    //Свайп вправо
    suspend fun likeUser(targetId: String): Result<LikeOutCome>

    //Свайп влево
    suspend fun passUser(targetId: String): Result<Unit>

    fun observeMatches(): Flow<List<Match>>
}