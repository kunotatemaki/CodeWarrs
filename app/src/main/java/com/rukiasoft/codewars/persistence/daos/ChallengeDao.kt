package com.rukiasoft.codewars.persistence.daos

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.RoomWarnings
import com.rukiasoft.codewars.persistence.entities.Challenge
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo

@Dao
abstract class ChallengeDao: BaseDao<Challenge>{

    fun getListChallengeAuthored(userName: String): DataSource.Factory<Int, ChallengeWithAllInfo> =
            getListChallengeInternal(userName, true)

    fun getListChallengeCompleted(userName: String): DataSource.Factory<Int, ChallengeWithAllInfo> =
            getListChallengeInternal(userName, false)

    @Query("SELECT * FROM challenge WHERE user_name LIKE :userName AND authored = :authored")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    protected abstract fun getListChallengeInternal(userName: String, authored: Boolean): DataSource.Factory<Int, ChallengeWithAllInfo>

    @Query("DELETE FROM challenge_tag")
    abstract fun deleteAll()


}