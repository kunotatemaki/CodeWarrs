package com.rukiasoft.codewars.ui.challenges

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo
import com.rukiasoft.codewars.repository.ChallengeRequests
import com.rukiasoft.codewars.utils.Constants
import com.rukiasoft.codewars.utils.switchMap
import com.rukiasoft.codewars.vo.AbsentLiveData
import com.rukiasoft.codewars.vo.Resource
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class ChallengesViewModel @Inject constructor(private val challengeRequests: ChallengeRequests,
                                              private val persistenceManager: PersistenceManager) : ViewModel() {

    enum class ChallengeTypes {
        COMPLETED,
        AUTHORED
    }

    private var refresh = false

    private var nextPageToDownload = 0

    val user: MediatorLiveData<UserInfo> = MediatorLiveData()

    var type = ChallengeTypes.COMPLETED

    private val nItemsTrigger = MutableLiveData<Long>()
    private val challengesTrigger = MutableLiveData<Long>()

    val numberOfChallenges: LiveData<Resource<Int>>

    val challenges: LiveData<PagedList<ChallengeWithAllInfo>>

    init {

        numberOfChallenges = nItemsTrigger.switchMap { _ ->
            if (user.value != null && user.value!!.userName.isNotBlank()) {
                updateChallenges()
            } else {
                AbsentLiveData.create()
            }
        }

        challenges = challengesTrigger.switchMap { _ ->
            if (user.value != null && user.value!!.userName.isNotBlank()) {
                if (type == ChallengeTypes.AUTHORED) {
                    persistenceManager.getChallengesAuthored(user.value!!.userName)
                } else {
                    persistenceManager.getChallengesCompleted(user.value!!.userName)
                }
            } else {
                AbsentLiveData.create()
            }
        }


    }

    fun resetRefresh() {
        refresh = false
    }

    fun setUserName(userName: String) {
        nextPageToDownload = 0
        user.addSource(persistenceManager.getUserInfo(userName), {
            user.value = it
            if (nItemsTrigger.value == null) {
                nItemsTrigger.value = System.currentTimeMillis()
            }
            if(challengesTrigger.value == null) {
                challengesTrigger.value = System.currentTimeMillis()
            }
        })
    }

    private fun updateChallenges(): LiveData<Resource<Int>> {
        return when (type) {
            ChallengesViewModel.ChallengeTypes.COMPLETED -> challengeRequests.getCompletedChallenges(
                    user.value!!.userName, user.value!!.lastFetchedCompleted ?: Date(0),
                    nextPageToDownload, refresh, Constants.DEFAULT_NUMBER_OF_RETRIES)
            ChallengesViewModel.ChallengeTypes.AUTHORED -> challengeRequests.getAuthoredChallenges(
                    user.value!!.userName, user.value!!.lastFetchedAuthored ?: Date(0),
                    refresh, Constants.DEFAULT_NUMBER_OF_RETRIES)
        }
    }

    fun setCompleted() {
        type = ChallengeTypes.COMPLETED
        challengesTrigger.value = System.currentTimeMillis()
        nItemsTrigger.value = System.currentTimeMillis()
    }

    fun setAuthored() {
        type = ChallengeTypes.AUTHORED
        challengesTrigger.value = System.currentTimeMillis()
        nItemsTrigger.value = System.currentTimeMillis()
    }

    fun refreshData() {
        refresh = true
        nItemsTrigger.value = System.currentTimeMillis()
    }

    fun isCompleted() = type == ChallengeTypes.COMPLETED

    fun getNumberOfCompletedChallenges() = user.value?.nItemsCompleted

    fun downloadNextPage() {
        user.value?.nPageCompleted?.let {
            nextPageToDownload++
            if (nextPageToDownload <= it) {
                Timber.d("descargo %d ", nextPageToDownload)
                refresh = true
                nItemsTrigger.value = System.currentTimeMillis()
            }
        }
    }
}