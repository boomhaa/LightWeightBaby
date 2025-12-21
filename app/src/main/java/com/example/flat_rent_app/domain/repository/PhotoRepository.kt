package com.example.flat_rent_app.domain.repository

import com.example.flat_rent_app.domain.model.ProfilePhoto
import java.io.File

interface PhotoRepository {
    suspend fun listProfilePhotos(): Result<List<ProfilePhoto>>

    suspend fun uploadPhoto(photoId: Int, file: File): Result<ProfilePhoto>

    suspend fun deletePhoto(photoId: Int): Result<Unit>
}