package com.example.profile.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.domain.User
import com.example.gitapp.di.MyApplication
import com.example.gitapp.framework.network.UserNetworkEntity
import com.example.gitapp.util.asUserDomain
import com.example.profile.databinding.ActivityProfileBinding
import com.example.profile.di.DaggerProfileComponent
import com.example.profile.di.ProfileComponent

class ProfileActivity : AppCompatActivity() {

    val profileComponent: ProfileComponent by lazy {
        initializeProfileComponent()
    }

    open fun initializeProfileComponent(): ProfileComponent {
        return DaggerProfileComponent.builder().appComponent((applicationContext as MyApplication).appComponent)
            .build()
    }
    
    lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser = intent.getParcelableExtra<UserNetworkEntity>("User")!!.asUserDomain()
        val view = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(view.root)
    }
}
