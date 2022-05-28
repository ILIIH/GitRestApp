package com.example.profile.profile.ProfileFragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.core.domain.User
import com.example.core.domain.helpers.Result
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.example.profile.profile.ProfileActivity

class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModels {
        (activity as ProfileActivity).profileComponent.viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUser: User = (activity as ProfileActivity).currentUser

        val view = FragmentProfileBinding.inflate(inflater, container, false)

        view.MemoryUsage.text = currentUser.disk_usage.toString() + " Mb"
        view.CountPublicRepository.text = "public repository : " + currentUser.public_repos.toString()
        view.NickName.text = currentUser.login
        Glide.with(view.Avatar.context).load(Uri.parse(currentUser.avatar_url))
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .apply(RequestOptions.skipMemoryCacheOf(true)).into(view.Avatar)

        val profileAdaptor = ProfileAdapter()
        view.RepositoryRecycler.adapter = profileAdaptor

        viewModel.setsUser(currentUser)

        viewModel.repo.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> profileAdaptor.addHeaderAndSubmitList(result.data)
                is Result.Error -> Toast.makeText(context, getString(R.string.RepositoryError), Toast.LENGTH_SHORT).show()
            }
        }

        view.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("newText", newText)
                profileAdaptor.getFilter().filter(newText)
                return false
            }
        })

        return view.root
    }
}
