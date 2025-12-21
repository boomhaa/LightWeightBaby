package com.example.flat_rent_app.data.remote.okhhtp

import com.example.flat_rent_app.core.FirebaseIdTokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthTokenInterceptor @Inject constructor(
    private val tokenProvider: FirebaseIdTokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenProvider.getIdToken() }

        val req = if (!token.isNullOrBlank()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else chain.request()

        return chain.proceed(req)
    }
}