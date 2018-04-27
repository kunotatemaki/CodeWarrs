package com.rukiasoft.codewars.ui.challenges

import android.support.v7.widget.RecyclerView
import com.rukiasoft.codewars.databinding.ItemChallengeBinding
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo

class ChallengeViewHolder(private val binding: ItemChallengeBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(challenge: ChallengeWithAllInfo?, callback: ChallengeAuthoredAdapter.ChallengeClickCallback) {
        binding.challenge = challenge
        binding.root.setOnClickListener {
            callback.onClick(binding.challenge)
        }
        binding.executePendingBindings()
    }
}