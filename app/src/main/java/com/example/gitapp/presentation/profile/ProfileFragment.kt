package com.example.gitapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.core.Domain.helpers.Result
import com.example.gitapp.R
import com.example.gitapp.databinding.FragmentProfileBinding
import com.example.gitapp.di.MyApplication
import javax.inject.Inject

class ProfileFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ProfileViewModelFactory
    lateinit var ViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity().applicationContext as MyApplication).appComponent.profileInject(this)

        val view = FragmentProfileBinding.inflate(inflater)
        val profileAdaptor = ProfileAdapter()
        view.RepositoryRecycler.adapter = profileAdaptor

        ViewModel = ViewModelProvider(this, vmFactory).get(ProfileViewModel::class.java)

        val currentUser = ProfileFragmentArgs.fromBundle(requireArguments()).user

        ViewModel.repo.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> profileAdaptor.addHeaderAndSubmitList(result.data, currentUser)
                is Result.Error -> Toast.makeText(context, getString(R.string.RepositoryError), Toast.LENGTH_SHORT).show()
            }
        }

        ViewModel.setsUser(currentUser)

        return view.root
    }
}
