package com.example.flat_rent_app.domain.model

data class Chat(
    val chatId: String = "",
    val otherUid: String,
    val lastMessageText: String? = null,
    val lastMessageAt: Long? = null,
    val unreadCount: Long = 0
)
