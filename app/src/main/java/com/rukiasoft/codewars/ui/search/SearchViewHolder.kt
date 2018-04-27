package com.rukiasoft.codewars.ui.search

import android.support.v7.widget.RecyclerView
import com.rukiasoft.codewars.databinding.ItemUserBinding
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo

class SearchViewHolder (private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserWithAllInfo, callback: SearchAdapter.UserCallback) {
        binding.user = user

        binding.root.setOnClickListener {
            callback.onClick(user)
        }

        binding.executePendingBindings()

    }
}