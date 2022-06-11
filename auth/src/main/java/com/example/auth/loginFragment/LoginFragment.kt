package com.example.auth.loginFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.auth.LoginActivity
import com.example.auth.R
import com.example.auth.databinding.FragmentLoginBinding
import com.example.core.domain.helpers.ErrorEntity
import com.example.core.domain.helpers.Result
import com.example.gitapp.util.asUserNetwork
import com.example.gitapp.util.hideKeyboard
import com.example.profile.profile.ProfileActivity

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels {
        (activity as LoginActivity).authComponent.viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(layoutInflater, container, false)

        binding.authTokenTextField.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.enterButton.requestFocus()
            }
        }

        binding.userNameTextField.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.authTokenTextField.requestFocus()
                    return@OnEditorActionListener true
                }
                false
            }
        )

        binding.authTokenTextField.setOnEditorActionListener(
            TextView.OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard(requireActivity())
                    binding.enterButton.requestFocus()
                    viewModel.autintificate(
                        binding.authToken.editText?.text.toString(),
                        binding.userName.editText?.text.toString()
                    )
                    return@OnEditorActionListener true
                }
                false
            }
        )

        viewModel.user.observe(requireActivity()) { result ->
            when (result) {
                is Result.Success -> {
                    val intent = Intent(context, ProfileActivity::class.java)
                    intent.putExtra("User", result.data.asUserNetwork())
                    startActivity(intent)
                }
                is Result.Error ->
                    when (result.error) {
                        is ErrorEntity.Credentials -> Toast.makeText(context, getString(R.string.CredentialsError), Toast.LENGTH_SHORT).show()
                        is ErrorEntity.Network -> Toast.makeText(context, getString(R.string.InternetError), Toast.LENGTH_SHORT).show()
                        is ErrorEntity.MissToken -> Toast.makeText(context, getString(R.string.TockenError), Toast.LENGTH_SHORT).show()
                    }
            }
        }

        binding.enterButton.setOnClickListener {
            viewModel.autintificate(binding.authToken.editText?.text.toString(), binding.userName.editText?.text.toString())
        }

        return binding.root
    }
}
