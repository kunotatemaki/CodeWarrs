package com.rukiasoft.codewars.persistence.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.utils.getDistinct

@Dao
abstract class UserInfoDao: BaseDao<UserInfo>{
    @Query("SELECT * FROM user_info ORDER BY last_fetched DESC LIMIT 5")
    protected abstract fun getListUsersByDateInternal(): LiveData<List<UserInfo>>

    fun getListUsersByDate(): LiveData<List<UserInfo>> =
            getListUsersByDateInternal().getDistinct()

    @Query("SELECT * FROM user_info ORDER BY overall_score DESC LIMIT 5")
    protected abstract fun getListUsersByRankInternal(): LiveData<List<UserInfo>>

    fun getListUsersByRank(): LiveData<List<UserInfo>> =
            getListUsersByRankInternal().getDistinct()

    @Query("SELECT * FROM user_info WHERE user_name LIKE :userName")
    protected abstract fun getUserInternal(userName: String): LiveData<UserInfo>

    fun getUsers(userName: String): LiveData<UserInfo> =
            getUserInternal(userName).getDistinct()

    @Query("DELETE FROM user_info")
    abstract fun deleteAll()
}