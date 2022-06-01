package com.example.auth

import android.content.Intent
import android.os.Bundle
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.auth.databinding.FragmentLoginBinding
import com.example.core.domain.helpers.ErrorEntity
import com.example.core.domain.helpers.Result
import com.example.di.AuthComponent
import com.example.di.DaggerAuthComponent
import com.example.gitapp.di.MyApplication
import com.example.gitapp.util.asUserNetwork
import com.example.gitapp.util.hideKeyboard
import com.example.profile.profile.ProfileActivity


class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels {
        authComponent.viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindidg = FragmentLoginBinding.inflate(layoutInflater)

        bindidg.AuthTokenTextField.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                bindidg.EnterButton.requestFocus()
            }
        })

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
                    hideKeyboard(this)
                    viewModel.autintificate(bindidg.AuthToken.editText?.text.toString(), bindidg.UserName.editText?.text.toString())
                    return@OnEditorActionListener true
                }
                false
            }
        )

        viewModel.user.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("User", result.data.asUserNetwork())
                    startActivity(intent)
                }
                is Result.Error ->
                    when (result.error) {
                        is ErrorEntity.Credentials -> Toast.makeText(this, getString(R.string.CredentialsError), Toast.LENGTH_SHORT).show()
                        is ErrorEntity.Network -> Toast.makeText(this, getString(R.string.InternetError), Toast.LENGTH_SHORT).show()
                        is ErrorEntity.MissTocken -> Toast.makeText(this, getString(R.string.TockenError), Toast.LENGTH_SHORT).show()
                    }
            }
        }

        bindidg.EnterButton.setOnClickListener {
            viewModel.autintificate(bindidg.AuthToken.editText?.text.toString(), bindidg.UserName.editText?.text.toString())
        }

        setContentView(bindidg.root)
    }

    private val authComponent: AuthComponent by lazy {
        initializeAuthComponent()
    }

    fun initializeAuthComponent(): AuthComponent {
        return DaggerAuthComponent.builder().appComponent((applicationContext as MyApplication).appComponent)
            .build()
    }
}
