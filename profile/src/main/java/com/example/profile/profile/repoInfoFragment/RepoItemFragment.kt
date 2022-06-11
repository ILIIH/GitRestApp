package com.example.profile.profile.repoInfoFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.profile.databinding.FragmentRepoItemBinding

class RepoItemFragment : Fragment() {

    private val args: RepoItemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentRepoItemBinding.inflate(inflater, container, false)
        view.repoName.text = args.repo.name
        view.language.text = args.repo.language
        view.description.text = args.repo.description
        val url = args.repo.url

        view.openCodeButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context?.startActivity(intent)
        }
        return view.root
    }
}
