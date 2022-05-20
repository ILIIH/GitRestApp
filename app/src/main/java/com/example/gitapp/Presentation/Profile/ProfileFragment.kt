package com.example.gitapp.Presentation.Profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.core.Domain.Repo
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

        var listR = LinkedList<Repo>()
        listR.add(Repo(1,"MyRepo","Name","Descroiption","asd",23,2,"Java"))

        profile_adaptor.addHeaderAndSubmitList(listR,current_user)

        ViewModel.setsUser(current_user)


        return view.root
    }


}