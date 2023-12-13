package com.syntax_institut.android_vl_b11chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.syntax_institut.android_vl_b11chat.databinding.ItemChatInBinding
import com.syntax_institut.android_vl_b11chat.databinding.ItemChatOutBinding
import com.syntax_institut.android_vl_b11chat.model.Message

class ChatAdapter(
    private val dataset: List<Message>,
    private val currentUserId: String
): RecyclerView.Adapter<ViewHolder>() {

    private val inType = 1
    private val outType = 2

    override fun getItemViewType(position: Int): Int {
        val item = dataset[position]
        return if (item.sender == currentUserId) {
            outType
        } else {
            inType
        }
    }

    inner class ChatInViewHolder(val binding: ItemChatInBinding): RecyclerView.ViewHolder(binding.root)
    inner class ChatOutViewHolder(val binding: ItemChatOutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == inType) {
            val binding = ItemChatInBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatInViewHolder(binding)
        } else {
            val binding = ItemChatOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ChatOutViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataset[position]

        if (holder is ChatInViewHolder) {
            holder.binding.tvChatIn.text = item.text
        } else if (holder is ChatOutViewHolder) {
            holder.binding.tvChatOut.text = item.text
        }

    }

}