package com.syntax_institut.android_vl_b11chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.syntax_institut.android_vl_b11chat.model.Chat
import com.syntax_institut.android_vl_b11chat.model.Message
import com.syntax_institut.android_vl_b11chat.model.Profile

class MainViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private var _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    lateinit var currentUserId: String
    lateinit var currentChat: DocumentReference

    init {
        if (firebaseAuth.currentUser != null) {
            currentUserId = firebaseAuth.currentUser!!.uid
        }
    }

    val profilesRef = firestore.collection("profiles")

    fun register(email: String, password: String, username: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = firebaseAuth.currentUser
                currentUserId = firebaseAuth.currentUser!!.uid
                profilesRef.document(firebaseAuth.currentUser!!.uid)
                    .set(Profile(username = username))
            } else {
                Log.e("AUTH", it.exception?.message.toString())
            }
        }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = firebaseAuth.currentUser
                currentUserId = firebaseAuth.currentUser!!.uid
            } else {
                Log.e("AUTH", it.exception?.message.toString())
            }
        }
    }

    fun setCurrentChat(chatPartnerUserId: String) {
        val chatId = createChatId(chatPartnerUserId, currentUserId)
        currentChat = firestore.collection("chats").document(chatId)

        currentChat.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val result = it.result
                if (result != null) {
                    if (!result.exists()) {
                        currentChat.set(Chat())
                    }
                }
            }
        }
    }

    fun sendNewMessage(text: String) {
        currentChat.update("messages", FieldValue.arrayUnion(Message(text, currentUserId)))
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }

    private fun createChatId(id1: String, id2: String): String {
        var ids = listOf(id1, id2)
        ids = ids.sorted()
        return ids[0] + ids[1]
    }

}