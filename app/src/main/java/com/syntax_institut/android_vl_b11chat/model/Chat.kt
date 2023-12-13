package com.syntax_institut.android_vl_b11chat.model

import com.google.firebase.firestore.DocumentId

data class Chat(
    @DocumentId
    val chatId: String = "",
    val messages: MutableList<Message> = mutableListOf()
)
