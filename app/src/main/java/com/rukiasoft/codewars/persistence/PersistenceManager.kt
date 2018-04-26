package com.rukiasoft.codewars.persistence

import android.arch.lifecycle.LiveData
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo

interface PersistenceManager {

    fun insertUserInfo(user: UserWithAllInfo)
    fun getListUsersByDate(): LiveData<List<UserInfo>>
    fun getListUsersByRank(): LiveData<List<UserInfo>>

    fun getUserInfo(userName: String): LiveData<UserInfo>


    fun deleteDb()

}