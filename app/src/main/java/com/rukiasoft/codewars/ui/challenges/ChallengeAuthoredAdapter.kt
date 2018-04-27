package com.rukiasoft.codewars.ui.challenges


import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.rukiasoft.codewars.R
import com.rukiasoft.codewars.databinding.GlideBindingComponent
import com.rukiasoft.codewars.databinding.ItemChallengeBinding
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo

/**
 * Created by Raul on 12/12/2017.
 * list adapter to retrieve paged data from db
 */
class ChallengeAuthoredAdapter constructor(private val challengeAuthoredClickCallback: ChallengeAuthoredAdapter.ChallengeClickCallback):
        PagedListAdapter<ChallengeWithAllInfo, ChallengeViewHolder>(diffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemChallengeBinding>(inflater, R.layout.item_challenge, parent,
                false, GlideBindingComponent())!!

        return ChallengeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge: ChallengeWithAllInfo? = getItem(position)
        holder.bind(challenge, challengeAuthoredClickCallback)
    }

    interface ChallengeClickCallback {
        fun onClick(challenge: ChallengeWithAllInfo?)
    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         *
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<ChallengeWithAllInfo>() {
            override fun areItemsTheSame(oldItem: ChallengeWithAllInfo, newItem: ChallengeWithAllInfo): Boolean =
                    oldItem.challenge?.id == newItem.challenge?.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: ChallengeWithAllInfo, newItem: ChallengeWithAllInfo): Boolean =
                    oldItem == newItem
        }
    }


}