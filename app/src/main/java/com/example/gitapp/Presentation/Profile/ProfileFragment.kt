package com.example.gitapp.Presentation.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.Domain.Repo
import com.example.core.Domain.helpers.Result
import com.example.gitapp.Presentation.Login.LoginFragmentDirections
import com.example.gitapp.Presentation.Login.LoginViewModel
import com.example.gitapp.Presentation.Login.LoginViewModelFactory
import com.example.gitapp.R
import com.example.gitapp.databinding.FragmentProfileBinding
import com.example.gitapp.di.MyApplication
import java.util.*
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var vmFactory : ProfileViewModelFactory
    lateinit var ViewModel : ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        (requireActivity().applicationContext as MyApplication).appComponent.ProfileInject(this)

        val view  = FragmentProfileBinding.inflate(inflater)
        var profile_adaptor = ProfileAdapter();
        view.RepositoryRecycler.adapter = profile_adaptor




        ViewModel =  ViewModelProvider(this,vmFactory).get(ProfileViewModel::class.java)

        val current_user = ProfileFragmentArgs.fromBundle(requireArguments()).user

        ViewModel.repo.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success ->profile_adaptor.addHeaderAndSubmitList(result.data,current_user)
                is Result.Error -> Toast.makeText(context, "Error could not download your repository", Toast.LENGTH_SHORT).show()
            }

        }




        ViewModel.setsUser(current_user)


        return view.root
    }


}