package com.rukiasoft.codewars.persistence.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rukiasoft.codewars.persistence.entities.ChallengeLanguageCompleted
import com.rukiasoft.codewars.persistence.entities.Skill
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.utils.getDistinct

@Dao
abstract class ChallengeLanguageCompletedDao: BaseDao<ChallengeLanguageCompleted>{


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: ChallengeLanguageCompleted): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(vararg obj: ChallengeLanguageCompleted): List<Long>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: MutableList<ChallengeLanguageCompleted>): List<Long>

    @Query("DELETE FROM challenge_language_completed")
    abstract fun deleteAll()
}