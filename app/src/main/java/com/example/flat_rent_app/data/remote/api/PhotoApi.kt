package com.example.flat_rent_app.data.remote.api

import com.example.flat_rent_app.data.remote.dto.ListPhotosResponseDto
import com.example.flat_rent_app.data.remote.dto.UploadPhotoResponseDto
import okhttp3.MultipartBody
import retrofit2.http.*

interface PhotoApi {

    @GET("/api/profile/photos")
    suspend fun listProfilePhotos(): ListPhotosResponseDto

    @Multipart
    @POST("/api/profile/photos/{slot}")
    suspend fun uploadPhoto(
        @Path("slot") slot: Int,
        @Part file: MultipartBody.Part
    ): UploadPhotoResponseDto

    @DELETE("/api/profile/photos/{slot}")
    suspend fun deletePhoto(@Path("slot") slot: Int)
}