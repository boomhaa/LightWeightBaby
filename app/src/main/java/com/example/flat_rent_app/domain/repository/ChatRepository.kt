package com.example.flat_rent_app.domain.repository

import com.example.flat_rent_app.domain.model.Chat
import com.example.flat_rent_app.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun observeMyChats(): Flow<List<Chat>>
    fun observeMessages(chatId: String, limit: Long = 50L): Flow<List<Message>>

    suspend fun sendMessage(chatId: String, otherId: String, text: String): Result<Unit>
    suspend fun markRead(chatId: String): Result<Unit>
}