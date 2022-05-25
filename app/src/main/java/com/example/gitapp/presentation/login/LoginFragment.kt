package com.example.gitapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.Domain.helpers.ErrorEntity
import com.example.core.Domain.helpers.Result
import com.example.gitapp.R
import com.example.gitapp.databinding.FragmentLoginBinding
import com.example.gitapp.util.asUserNetwor
import com.example.gitapp.util.getAppComponent
import com.example.gitapp.util.hideKeyboard

class LoginFragment : Fragment() {

    val viewModel: LoginViewModel by viewModels {
        getAppComponent().viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bindidg = FragmentLoginBinding.inflate(inflater)

        bindidg.UserNameTextField.setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    bindidg.AuthTokenTextField.requestFocus()
                    return@OnEditorActionListener true
                }
                false
            }
        )

        bindidg.AuthTokenTextField.setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(requireActivity())
                    viewModel.autintificate(bindidg.AuthToken.editText?.text.toString(), bindidg.UserName.editText?.text.toString())
                    return@OnEditorActionListener true
                }
                false
            }
        )

        viewModel.user.observe(requireActivity()) { result ->
            when (result) {
                is Result.Success ->
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment(result.data.asUserNetwor()))
                is Result.Error ->
                    when (result.error) {
                        is ErrorEntity.Credentials -> Toast.makeText(context, getString(R.string.CredentialsError), Toast.LENGTH_SHORT).show()
                        is ErrorEntity.Network -> Toast.makeText(context, getString(R.string.InternetError), Toast.LENGTH_SHORT).show()
                        is ErrorEntity.MissTocken -> Toast.makeText(context, getString(R.string.TockenError), Toast.LENGTH_SHORT).show()
                    }
            }
        }

        bindidg.EnterButton.setOnClickListener {
            viewModel.autintificate(bindidg.AuthToken.editText?.text.toString(), bindidg.UserName.editText?.text.toString())
        }

        return bindidg.root
    }
}
