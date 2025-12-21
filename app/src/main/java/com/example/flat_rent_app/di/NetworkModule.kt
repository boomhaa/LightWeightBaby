package com.example.flat_rent_app.di

import com.example.flat_rent_app.data.remote.okhhtp.AuthTokenInterceptor
import com.example.flat_rent_app.data.remote.api.PhotoApi
import com.example.flat_rent_app.util.Constants
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideOkHttp(
        authTokenInterceptor: AuthTokenInterceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(authTokenInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttp: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        val baseUrl = Constants.PHOTO_BASE_URL
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun providePhotoApi(retrofit: Retrofit): PhotoApi =
        retrofit.create(PhotoApi::class.java)
}