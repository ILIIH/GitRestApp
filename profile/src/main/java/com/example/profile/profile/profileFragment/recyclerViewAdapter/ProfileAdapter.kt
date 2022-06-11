package com.example.profile.profile.profileFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.updateLayoutParams
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.core.domain.Repo
import com.example.gitapp.util.asRepoNetworkEntity
import com.example.profile.databinding.RepoItemBinding
import java.util.*

class ProfileAdapter :
    PagingDataAdapter<Pair<Repo, Boolean>, ProfileAdapter.RepoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////

    class RepoViewHolder private constructor(val binding: RepoItemBinding) : ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Pair<Repo, Boolean>) {
            
            if (item.second) {
                with(item.first) {
                    binding.body.visibility = View.VISIBLE
                    binding.body.updateLayoutParams { height = WRAP_CONTENT }
                    binding.repoName.text = name
                    binding.language.text = language
                    binding.stars.text = "$stars⭐"

                    binding.root.setOnClickListener {
                        it.findNavController().navigate(ProfileFragmentDirections.toRepoDetail(asRepoNetworkEntity()))
                    }
                }
            } else {
                binding.body.visibility = View.GONE
                binding.body.updateLayoutParams { height = 0 }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RepoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RepoItemBinding.inflate(layoutInflater, parent, false)
                return RepoViewHolder(binding)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filtrate(constraint: CharSequence) {

        this@ProfileAdapter.snapshot().items.map {
            it.second = it.first.name.lowercase(Locale.getDefault()).contains(constraint)
        }
        this@ProfileAdapter.notifyDataSetChanged()
    }
}

class DiffCallback : DiffUtil.ItemCallback<Pair<Repo, Boolean>>() {
    override fun areItemsTheSame(oldItem: Pair<Repo, Boolean>, newItem: Pair<Repo, Boolean>): Boolean {
        return oldItem.first.fullName == newItem.first.fullName
    }
    override fun areContentsTheSame(oldItem: Pair<Repo, Boolean>, newItem: Pair<Repo, Boolean>): Boolean {
        return oldItem == newItem
    }
}

data class Pair<A, B>(var first: A, var second: B)
