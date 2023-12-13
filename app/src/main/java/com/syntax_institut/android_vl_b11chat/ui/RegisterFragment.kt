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
import com.syntax_institut.android_vl_b11chat.databinding.FragmentRegisterBinding

class RegisterFragment: Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btBackToLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.btRegister.setOnClickListener {
            val email = binding.tietEmailRegister.text.toString()
            val password = binding.tietPasswordRegister.text.toString()
            val username = binding.tietUsername.text.toString()

            if (email != "" && password != "" && username != "") {
                viewModel.register(email, password, username)
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }

    }

}