package com.rukiasoft.codewars.persistence.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
import com.rukiasoft.codewars.utils.getDistinct
import java.util.*

@Dao
abstract class UserInfoDao: BaseDao<UserInfo>{
    @Query("SELECT * FROM user_info ORDER BY last_fetched_info DESC LIMIT 5")
    protected abstract fun getListUsersByDateInternal(): LiveData<List<UserWithAllInfo>>

    fun getListUsersByDate(): LiveData<List<UserWithAllInfo>> =
            getListUsersByDateInternal().getDistinct()

    @Query("SELECT * FROM user_info ORDER BY leader_board_position ASC LIMIT 5")
    protected abstract fun getListUsersByRankInternal(): LiveData<List<UserWithAllInfo>>

    fun getListUsersByRank(): LiveData<List<UserWithAllInfo>> =
            getListUsersByRankInternal().getDistinct()

    @Query("SELECT * FROM user_info WHERE user_name LIKE :userName")
    protected abstract fun getUserInternal(userName: String): LiveData<UserInfo>

    fun getUser(userName: String): LiveData<UserInfo> =
            getUserInternal(userName).getDistinct()

    @Query("SELECT * FROM user_info WHERE user_name LIKE :userName")
    abstract fun getUserWithoutLiveData(userName: String): UserInfo?

    @Query("UPDATE user_info SET last_fetched_authored = :authoredDate WHERE user_name LIKE :userName")
    abstract fun storeAuthoredInfo(authoredDate: Date, userName: String)

    @Query("UPDATE user_info SET last_fetched_completed = :authoredComplete, n_page_completed = :pages, n_items_completed = :items WHERE user_name LIKE :userName")
    abstract fun storeCompletedInfo(pages: Int?, items: Int?, authoredComplete: Date, userName: String)

    @Query("UPDATE user_info SET last_page_downloaded = :page WHERE user_name LIKE :userName")
    abstract fun setLastPageDownloaded(page: Int, userName: String)

    @Query("DELETE FROM user_info")
    abstract fun deleteAll()


}