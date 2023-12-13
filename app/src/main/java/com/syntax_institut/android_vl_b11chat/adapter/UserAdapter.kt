package com.syntax_institut.android_vl_b11chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.syntax_institut.android_vl_b11chat.MainViewModel
import com.syntax_institut.android_vl_b11chat.R
import com.syntax_institut.android_vl_b11chat.databinding.ItemUserBinding
import com.syntax_institut.android_vl_b11chat.model.Profile

class UserAdapter(
    private val dataset: List<Profile>,
    private val viewModel: MainViewModel
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = dataset[position]
        holder.binding.tvUsername.text = item.username
        holder.binding.cvUser.setOnClickListener {
            viewModel.setCurrentChat(item.userId)
            holder.itemView.findNavController().navigate(R.id.chatFragment)
        }
    }

}