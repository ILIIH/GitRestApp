package com.example.gitapp.Presentation.Login

import android.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.Domain.helpers.ErrorEntity
import com.example.core.Domain.helpers.Result
import com.example.gitapp.databinding.FragmentLoginBinding
import com.example.gitapp.di.MyApplication
import com.example.gitapp.util.hideKeyboard
import javax.inject.Inject


class LoginFragment : Fragment() {



    @Inject
    lateinit var vmFactory : LoginViewModelFactory
    lateinit var ViewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindidg = FragmentLoginBinding.inflate(inflater)

        (requireActivity().applicationContext as MyApplication).appComponent.LoginInject(this)


        bindidg.AuthTokenTextField.onCheckIsTextEditor()



        bindidg.UserNameTextField.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                bindidg.AuthTokenTextField.requestFocus()
                return@OnEditorActionListener true
            }
            false
        })

        bindidg.AuthTokenTextField.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                bindidg.EnterButton.requestFocus()
                hideKeyboard(requireActivity());
                return@OnEditorActionListener true
            }
            false
        })

        ViewModel =  ViewModelProvider(this,vmFactory).get(LoginViewModel::class.java)


        ViewModel.user.observe( requireActivity()) { result -> when(result) {
            is Result.Success ->
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment(result.data))
            is Result.Error ->
                when (result.error){
                is ErrorEntity.Credentials -> Toast.makeText(context, "Error wrong credentials", Toast.LENGTH_SHORT).show()
                is ErrorEntity.Network -> Toast.makeText(context, "Error no internet", Toast.LENGTH_SHORT).show()
                is ErrorEntity.MissTocken -> Toast.makeText(context, "Wrong token", Toast.LENGTH_SHORT).show()
            }
        }
        }




        bindidg.EnterButton.setOnClickListener {
            ViewModel.autintificate(bindidg.AuthToken.editText!!.text.toString(),bindidg.UserName.editText!!.text.toString() )
        }

        return bindidg.root
    }




}