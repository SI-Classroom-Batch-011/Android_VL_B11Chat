package com.syntax_institut.android_vl_b11chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syntax_institut.android_vl_b11chat.MainViewModel
import com.syntax_institut.android_vl_b11chat.adapter.ChatAdapter
import com.syntax_institut.android_vl_b11chat.databinding.FragmentChatBinding
import com.syntax_institut.android_vl_b11chat.model.Chat

class ChatFragment: Fragment() {

    private lateinit var binding: FragmentChatBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentChat.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                val chat = value.toObject(Chat::class.java)
                binding.rvChatMessages.adapter = ChatAdapter(chat!!.messages, viewModel.currentUserId)
            }
        }

        binding.btSend.setOnClickListener {
            val text = binding.tietMessage.text.toString()
            if (text != "") {
                viewModel.sendNewMessage(text)
            }
        }

    }

}