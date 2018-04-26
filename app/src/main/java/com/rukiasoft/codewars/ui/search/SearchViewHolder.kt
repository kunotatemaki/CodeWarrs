package com.rukiasoft.codewars.ui.search

import android.support.v7.widget.RecyclerView
import com.rukiasoft.codewars.databinding.ItemUserBinding
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
import com.rukiasoft.codewars.utils.DateUtils

class SearchViewHolder (private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserWithAllInfo) {
        binding.user = user.user
        user.languages?.let {
            if(it.isNotEmpty()) {
                binding.bestLanguage = user.languages?.first()
            }
        }

        binding.dateFetched = DateUtils.getDateFormatted(user.user?.lastFetched)

        binding.executePendingBindings()

    }
}