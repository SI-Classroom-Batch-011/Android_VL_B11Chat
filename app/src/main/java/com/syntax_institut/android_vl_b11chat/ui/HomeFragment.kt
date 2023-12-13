package com.syntax_institut.android_vl_b11chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.syntax_institut.android_vl_b11chat.MainViewModel
import com.syntax_institut.android_vl_b11chat.R
import com.syntax_institut.android_vl_b11chat.adapter.UserAdapter
import com.syntax_institut.android_vl_b11chat.databinding.FragmentHomeBinding
import com.syntax_institut.android_vl_b11chat.model.Profile

class HomeFragment: Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btLogout.setOnClickListener {
            viewModel.logout()
        }

        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it == null) {
                findNavController().navigate(R.id.loginFragment)
            }
        }

        viewModel.profilesRef.addSnapshotListener { value, error ->
            if (error == null && value != null) {
                val userList = value.map { it.toObject(Profile::class.java) }.toMutableList()
                userList.removeAll { it.userId == viewModel.currentUserId }
                binding.rvUsers.adapter = UserAdapter(userList, viewModel)
            }
        }

    }

}