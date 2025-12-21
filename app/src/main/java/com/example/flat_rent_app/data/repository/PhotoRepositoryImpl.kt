package com.example.flat_rent_app.data.repository

import com.example.flat_rent_app.data.remote.api.PhotoApi
import com.example.flat_rent_app.data.remote.asImage
import com.example.flat_rent_app.data.remote.toDomain
import com.example.flat_rent_app.domain.model.ProfilePhoto
import com.example.flat_rent_app.domain.repository.PhotoRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepositoryImpl @Inject constructor(
    private val api: PhotoApi
) : PhotoRepository {
    override suspend fun listProfilePhotos(): Result<List<ProfilePhoto?>> = runCatching {
        val dto = api.listProfilePhotos()
        val slots = MutableList<ProfilePhoto?>(3) { null }
        val now = System.currentTimeMillis()

        dto.slots.forEach { item ->
            val urls = item.urls
            if (item.slot in 0..2 && urls != null) {
                slots[item.slot] = urls.toDomain(now)
            }
        }
        slots
    }.recoverCatching { t ->
        throw RuntimeException(t.message ?: "list error", t)
    }


    override suspend fun uploadPhoto(
        photoId: Int,
        file: File
    ): Result<ProfilePhoto> =
        runCatching {
            require(photoId in 0..2)
            val res = api.uploadPhoto(photoId, file.asImage("file"))
            res.urls.toDomain(System.currentTimeMillis())
        }.recoverCatching { t ->
            throw RuntimeException(t.message ?: "upload error", t)
        }

    override suspend fun deletePhoto(photoId: Int): Result<Unit> = runCatching {
        require(photoId in 0..2)
        api.deletePhoto(photoId)
    }.recoverCatching { t ->
        throw RuntimeException(t.message ?: "delete error", t)
    }
}