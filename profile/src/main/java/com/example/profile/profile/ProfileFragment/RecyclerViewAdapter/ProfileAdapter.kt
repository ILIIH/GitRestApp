package com.example.profile.profile.ProfileFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.core.domain.Repo
import com.example.profile.databinding.RepoItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ProfileAdapter() :
    ListAdapter<UiModel, ProfileAdapter.RepoViewHolder>(DiffCallback()),
    Filterable {

    private val dataSet = ArrayList<UiModel.RepoItem>()
    private val FullList = ArrayList<UiModel.RepoItem>()
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = getItem(position) as UiModel.RepoItem
        holder.bind(item.repo)
    }

    fun addHeaderAndSubmitList(list: List<Repo>) {
        adapterScope.launch {
            val items = list.map { UiModel.RepoItem(it) }
            FullList.addAll(items)
            dataSet.addAll(items)
            withContext(Dispatchers.Main) {
                submitList(dataSet as List<UiModel>?)
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
            fun from(parent: ViewGroup): RepoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RepoItemBinding.inflate(layoutInflater, parent, false)
                return RepoViewHolder(binding)
            }
        }
    }

    override fun getFilter(): Filter {
        return Searched_Filter
    }

    private val Searched_Filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: ArrayList<UiModel.RepoItem> = ArrayList()
            if (constraint == null || constraint.length == 0) {
                filteredList.addAll(FullList)
            } else {
                val filterPattern =
                    constraint.toString().lowercase(Locale.getDefault()).trim { it <= ' ' }
                for (item in FullList) {

                    if (item.repo.fullName.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            dataSet.clear()
            dataSet.addAll(results.values as ArrayList<UiModel.RepoItem>)
            notifyDataSetChanged()
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
}
