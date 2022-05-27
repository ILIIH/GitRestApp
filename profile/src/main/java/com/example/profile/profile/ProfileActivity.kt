package com.example.profile.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.core.domain.User
import com.example.core.domain.helpers.Result
import com.example.gitapp.util.asUserDomain
import com.example.gitapp.di.MyApplication
import com.example.gitapp.framework.network.UserNetworkEntity
import com.example.profile.R
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
    
    val viewModel: ProfileViewModel by viewModels {
        profileComponent.viewModelsFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser: User = intent.getParcelableExtra<UserNetworkEntity>("User")!!.asUserDomain()

        val view = ActivityProfileBinding.inflate(layoutInflater)
        val profileAdaptor = com.example.profile.profile.ProfileAdapter()
        view.RepositoryRecycler.adapter = profileAdaptor

        viewModel.setsUser(currentUser)
        
        viewModel.repo.observe(this) { result ->
            when (result) {
                is Result.Success -> profileAdaptor.addHeaderAndSubmitList(result.data, currentUser)
                is Result.Error -> Toast.makeText(this, getString(R.string.RepositoryError), Toast.LENGTH_SHORT).show()
            }
        }

        setContentView(view.root)
    }
}
