package com.rukiasoft.codewars.persistence.daos

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.RoomWarnings
import com.rukiasoft.codewars.persistence.entities.Challenge
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo
import com.rukiasoft.codewars.utils.getDistinct

@Dao
abstract class ChallengeDao: BaseDao<Challenge>{

    fun getListChallengeAuthored(userName: String): DataSource.Factory<Int, ChallengeWithAllInfo> =
            getListChallengeInternal(userName, true)

    fun getListChallengeCompleted(userName: String): DataSource.Factory<Int, ChallengeWithAllInfo> =
            getListChallengeInternal(userName, false)

    @Query("SELECT * FROM challenge WHERE user_name LIKE :userName AND authored = :authored")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    protected abstract fun getListChallengeInternal(userName: String, authored: Boolean): DataSource.Factory<Int, ChallengeWithAllInfo>


    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT COUNT(id) FROM challenge WHERE user_name LIKE :userName AND authored = :authored")
    protected abstract fun getNumberOfChallengesInternal(userName: String, authored: Boolean): LiveData<Int>

    fun getNumberOfChallenges(userName: String, authored: Boolean): LiveData<Int> =
            getNumberOfChallengesInternal(userName, authored).getDistinct()

    @Query("DELETE FROM challenge_tag")
    abstract fun deleteAll()



}