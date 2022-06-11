package com.example.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.auth.databinding.LoginActivityBinding
import com.example.di.AuthComponent
import com.example.di.DaggerAuthComponent
import com.example.gitapp.di.MyApplication

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val view = LoginActivityBinding.inflate(layoutInflater)
        setContentView(view.root)
    }

    val authComponent: AuthComponent by lazy {
        initializeAuthComponent()
    }

    private fun initializeAuthComponent(): AuthComponent {
        return DaggerAuthComponent.builder().appComponent((applicationContext as MyApplication).appComponent)
            .build()
    }
}
