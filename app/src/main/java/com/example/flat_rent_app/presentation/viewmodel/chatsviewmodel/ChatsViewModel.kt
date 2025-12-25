package com.example.flat_rent_app.presentation.viewmodel.chatsviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flat_rent_app.domain.model.ChatUiItem
import com.example.flat_rent_app.domain.repository.ChatRepository
import com.example.flat_rent_app.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    chatRepo: ChatRepository,
    private val profileRepo: ProfileRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val items: StateFlow<List<ChatUiItem>> =
        chatRepo.observeMyChats()
            .flatMapLatest { chats ->
                if (chats.isEmpty()) return@flatMapLatest flowOf(emptyList())

                combine(
                    chats.map { chat ->
                        profileRepo.observerProfile(chat.otherUid)
                            .map { p -> ChatUiItem(chat, p) }
                    }
                ) { arr -> arr.toList() }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}