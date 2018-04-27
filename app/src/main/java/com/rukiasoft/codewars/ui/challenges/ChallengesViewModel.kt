package com.rukiasoft.codewars.ui.challenges

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.repository.ChallengeRequests
import com.rukiasoft.codewars.utils.Constants
import com.rukiasoft.codewars.utils.switchMap
import com.rukiasoft.codewars.vo.Resource
import java.util.*
import javax.inject.Inject

class ChallengesViewModel @Inject constructor(private val challengeRequests: ChallengeRequests,
                                              private val persistenceManager: PersistenceManager) : ViewModel() {

    enum class ChallengeTypes{
        COMPLETED,
        AUTHORED
    }

    private var userName: String = ""

    var type = ChallengeTypes.AUTHORED

    private val query = MutableLiveData<Long>()

    val challenges: LiveData<Resource<Int>>

    init {

        challenges = query.switchMap { _ ->
            getNumberOfChallenges()
        }
    }

    fun setUserName(userName: String){
        this.userName = userName
        query.value = System.currentTimeMillis()
    }

    private fun getNumberOfChallenges(): LiveData<Resource<Int>> {
        return when(type) {
            ChallengesViewModel.ChallengeTypes.COMPLETED ->  challengeRequests.getAuthoredChallenges(userName, Date(0), true, Constants.DEFAULT_NUMBER_OF_RETRIES)
            ChallengesViewModel.ChallengeTypes.AUTHORED -> challengeRequests.getAuthoredChallenges(userName, Date(0), true, Constants.DEFAULT_NUMBER_OF_RETRIES)
        }
    }

}