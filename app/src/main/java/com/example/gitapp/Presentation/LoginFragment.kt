package com.example.gitapp.Presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gitapp.R
import com.example.gitapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindidg = FragmentLoginBinding.inflate(inflater)

        bindidg.EnterButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)

        }

        return bindidg.root
    }


}