package com.rukiasoft.codewars.ui.challenges

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo
import com.rukiasoft.codewars.repository.UserInfoRequests
import com.rukiasoft.codewars.utils.switchMap
import javax.inject.Inject

class ChallengesViewModel @Inject constructor(private val userInfoRequests: UserInfoRequests,
                                              private val persistenceManager: PersistenceManager) : ViewModel() {

    enum class ChallengeTypes{
        COMPLETED,
        AUTHORED
    }

    private var userName: String = ""

    var type = ChallengeTypes.AUTHORED

    private val query = MutableLiveData<Long>()

    val challenges: LiveData<PagedList<ChallengeWithAllInfo>>

    init {

        challenges = query.switchMap { _ ->
            getListOfChallenges()
        }
    }

    fun setUserName(userName: String){
        this.userName = userName
        query.value = System.currentTimeMillis()
    }

    private fun getListOfChallenges(): LiveData<PagedList<ChallengeWithAllInfo>> {
        return when(type) {
            ChallengesViewModel.ChallengeTypes.COMPLETED -> persistenceManager.getChallengesCompleted(userName)
            ChallengesViewModel.ChallengeTypes.AUTHORED -> persistenceManager.getChallengesAuthored(userName)
        }
    }

}