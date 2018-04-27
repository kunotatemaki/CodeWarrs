package com.rukiasoft.codewars.persistence

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
import com.rukiasoft.codewars.persistence.utils.ChallengesToStore

interface PersistenceManager {

    fun insertUserInfo(user: UserWithAllInfo)
    fun getListUsersByDate(): LiveData<List<UserWithAllInfo>>
    fun getListUsersByRank(): LiveData<List<UserWithAllInfo>>

    fun getUserInfo(userName: String): LiveData<UserInfo>
    fun getUserInfoWithoutLiveData(userName: String): UserInfo?

    fun getChallengesCompleted(userName: String): LiveData<PagedList<ChallengeWithAllInfo>>
    fun getChallengesAuthored(userName: String): LiveData<PagedList<ChallengeWithAllInfo>>
    fun getNumberChallengesAuthored(userName: String): LiveData<Int>
    fun insertChallenges(challengesToStore: ChallengesToStore)

    fun deleteDb()

}