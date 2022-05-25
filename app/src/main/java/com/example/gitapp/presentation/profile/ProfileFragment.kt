package com.example.gitapp.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.Domain.helpers.Result
import com.example.gitapp.R
import com.example.gitapp.databinding.FragmentProfileBinding
import com.example.gitapp.util.asUserDomain
import com.example.gitapp.util.getAppComponent

class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModels {
        getAppComponent().viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = FragmentProfileBinding.inflate(inflater)
        val profileAdaptor = ProfileAdapter()
        view.RepositoryRecycler.adapter = profileAdaptor

        val currentUser = ProfileFragmentArgs.fromBundle(requireArguments()).user

        viewModel.repo.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> profileAdaptor.addHeaderAndSubmitList(result.data, currentUser.asUserDomain())
                is Result.Error -> Toast.makeText(context, getString(R.string.RepositoryError), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.setsUser(currentUser.asUserDomain())

        return view.root
    }
}
