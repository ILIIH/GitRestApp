package com.example.gitapp.presentation.profile

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.core.Domain.Repo
import com.example.core.Domain.User
import com.example.gitapp.databinding.HeadBinding
import com.example.gitapp.databinding.RepoItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileAdapter() : ListAdapter<UiModel, ViewHolder>(DiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    private val ITEM_VIEW_TYPE_HEADER = 1
    private val ITEM_VIEW_TYPE_ITEM = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeadViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> RepoViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.ProfileItem -> ITEM_VIEW_TYPE_HEADER
            is UiModel.RepoItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is HeadViewHolder -> {
                val item = getItem(position) as UiModel.ProfileItem
                holder.bind(item.profile)
            }
            is RepoViewHolder -> {
                val item = getItem(position) as UiModel.RepoItem
                holder.bind(item.repo)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<Repo>, user: User) {
        adapterScope.launch {

            val items = listOf(UiModel.ProfileItem(user)) + list.map { UiModel.RepoItem(it) }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////

    class HeadViewHolder private constructor(val binding: HeadBinding) : ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: User) {
            Glide.with(binding.Avatar.context).load(Uri.parse(item.avatar_url))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true)).into(binding.Avatar)
            binding.NickName.text = item.login
            binding.CountPublicRepository.text = "public repository : " + item.public_repos.toString()
            binding.MemoryUsage.text = item.disk_usage.toString() + " Mb"
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeadBinding.inflate(layoutInflater, parent, false)
                return HeadViewHolder(binding)
            }
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////

    class RepoViewHolder private constructor(val binding: RepoItemBinding) : ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Repo) {
            binding.RepoName.text = item.name
            binding.Language.text = item.language
            binding.Stars.text = item.stars.toString() + "⭐"
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RepoItemBinding.inflate(layoutInflater, parent, false)
                return RepoViewHolder(binding)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<UiModel>() {
    override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
        return oldItem == newItem
    }
}

sealed class UiModel {
    abstract val id: Long

    data class RepoItem(val repo: Repo) : UiModel() {
        override val id = repo.id
    }

    data class ProfileItem(val profile: User) : UiModel() {
        override val id = Long.MIN_VALUE
    }
}
