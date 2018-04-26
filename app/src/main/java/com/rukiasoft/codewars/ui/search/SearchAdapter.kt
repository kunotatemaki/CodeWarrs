package com.rukiasoft.codewars.ui.search

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rukiasoft.codewars.R
import com.rukiasoft.codewars.databinding.GlideBindingComponent
import com.rukiasoft.codewars.databinding.ItemUserBinding
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo

class SearchAdapter constructor(private val userCallback: UserCallback, private val listOfUsers: List<UserWithAllInfo>):
        RecyclerView.Adapter<SearchViewHolder>() {


    override fun getItemCount() = listOfUsers.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemUserBinding>(inflater, R.layout.item_user, parent,
                false, GlideBindingComponent())!!

        return SearchViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val user: UserWithAllInfo = listOfUsers[position]
        holder.bind(user, userCallback)
    }

    interface UserCallback {
        fun onClick(user: UserWithAllInfo?)
    }

}