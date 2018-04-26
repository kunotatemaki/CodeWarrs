package com.rukiasoft.codewars.persistence.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rukiasoft.codewars.persistence.entities.Challenge
import com.rukiasoft.codewars.persistence.entities.Skill
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.relations.ChallengeWithAllInfo
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
import com.rukiasoft.codewars.utils.getDistinct

@Dao
abstract class ChallengeDao: BaseDao<Challenge>{

    @Query("SELECT * FROM challenge WHERE user_name = :userName AND authored = :authored")
    protected abstract fun getListChallengeInternal(userName: String, authored: Boolean): LiveData<List<ChallengeWithAllInfo>>

    fun getListChallengeAuthored(userName: String): LiveData<List<ChallengeWithAllInfo>> =
            getListChallengeInternal(userName, true).getDistinct()

    fun getListChallengeCompleted(userName: String): LiveData<List<ChallengeWithAllInfo>> =
            getListChallengeInternal(userName, false).getDistinct()


    @Query("DELETE FROM challenge_tag")
    abstract fun deleteAll()
}