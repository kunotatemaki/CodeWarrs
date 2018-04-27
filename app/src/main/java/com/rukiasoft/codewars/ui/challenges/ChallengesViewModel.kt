package com.rukiasoft.codewars.ui.challenges

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo
import com.rukiasoft.codewars.repository.ChallengeRequests
import com.rukiasoft.codewars.utils.Constants
import com.rukiasoft.codewars.utils.switchMap
import com.rukiasoft.codewars.vo.AbsentLiveData
import com.rukiasoft.codewars.vo.Resource
import java.util.*
import javax.inject.Inject

class ChallengesViewModel @Inject constructor(private val challengeRequests: ChallengeRequests,
                                              private val persistenceManager: PersistenceManager) : ViewModel() {

    enum class ChallengeTypes{
        COMPLETED,
        AUTHORED
    }

    private var refresh = true

    private var userName: String = ""

    var type = ChallengeTypes.COMPLETED

    private val query = MutableLiveData<Long>()

    val numberOfChallenges: LiveData<Resource<Int>>

    val challenges: LiveData<PagedList<ChallengeWithAllInfo>>

    init {

        numberOfChallenges = query.switchMap { _ ->
            if(userName.isNotBlank()) {
                updateChallenges()
            }else{
                AbsentLiveData.create()
            }
        }

        challenges = query.switchMap { _ ->
            if(userName.isNotBlank()) {
                if(type == ChallengeTypes.AUTHORED) {
                    persistenceManager.getChallengesAuthored(userName)
                }else {
                    persistenceManager.getChallengesCompleted(userName)
                }
            }else{
                AbsentLiveData.create()
            }
        }
    }

    fun setUserName(userName: String){
        this.userName = userName
        query.value = System.currentTimeMillis()
    }

    private fun updateChallenges(): LiveData<Resource<Int>> {
        return when(type){
            ChallengesViewModel.ChallengeTypes.COMPLETED -> challengeRequests.getCompletedChallenges(userName, Date(0), 0, refresh, Constants.DEFAULT_NUMBER_OF_RETRIES)
            ChallengesViewModel.ChallengeTypes.AUTHORED -> challengeRequests.getAuthoredChallenges(userName, Date(0), refresh, Constants.DEFAULT_NUMBER_OF_RETRIES)
        }

    }

    fun setCompleted() {
        type = ChallengeTypes.COMPLETED
        query.value = System.currentTimeMillis()
    }
    fun setAuthored() {
        type = ChallengeTypes.AUTHORED
        query.value = System.currentTimeMillis()
    }

}