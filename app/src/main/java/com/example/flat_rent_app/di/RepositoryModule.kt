package com.example.flat_rent_app.di

import com.example.flat_rent_app.data.remote.api.PhotoApi
import com.example.flat_rent_app.data.repository.AuthRepositoryImpl
import com.example.flat_rent_app.data.repository.ChatRepositoryImpl
import com.example.flat_rent_app.data.repository.PhotoRepositoryImpl
import com.example.flat_rent_app.data.repository.ProfileRepositoryImpl
import com.example.flat_rent_app.data.repository.SwipeRepositoryImpl
import com.example.flat_rent_app.domain.repository.AuthRepository
import com.example.flat_rent_app.domain.repository.ChatRepository
import com.example.flat_rent_app.domain.repository.PhotoRepository
import com.example.flat_rent_app.domain.repository.ProfileRepository
import com.example.flat_rent_app.domain.repository.SwipeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideProfileRepository(
        db: FirebaseFirestore,
        authRepository: AuthRepository
    ): ProfileRepository = ProfileRepositoryImpl(db, authRepository)

    @Provides
    @Singleton
    fun provideSwipeRepository(
        db: FirebaseFirestore,
        authRepository: AuthRepository
    ): SwipeRepository = SwipeRepositoryImpl(db, authRepository)

    @Provides
    @Singleton
    fun provideChatRepository(
        db: FirebaseFirestore,
        authRepository: AuthRepository
    ): ChatRepository = ChatRepositoryImpl(db, authRepository)

    @Provides
    @Singleton
    fun providePhotoRepository(
        api: PhotoApi
    ): PhotoRepository = PhotoRepositoryImpl(api)
}