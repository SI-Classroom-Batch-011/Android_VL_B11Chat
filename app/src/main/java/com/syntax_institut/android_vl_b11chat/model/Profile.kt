package com.syntax_institut.android_vl_b11chat.model

import com.google.firebase.firestore.DocumentId

data class Profile(
    val username: String = "",
    @DocumentId
    val userId: String = ""
)
