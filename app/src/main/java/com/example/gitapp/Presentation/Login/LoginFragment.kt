package com.example.gitapp.Presentation.Login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.Domain.helpers.ErrorEntity
import com.example.gitapp.databinding.FragmentLoginBinding
import com.example.gitapp.di.MyApplication
import javax.inject.Inject
import com.example.core.Domain.helpers.Result


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