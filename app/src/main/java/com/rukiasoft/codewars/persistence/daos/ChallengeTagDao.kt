package com.rukiasoft.codewars.persistence.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rukiasoft.codewars.persistence.entities.ChallengeTag

@Dao
abstract class ChallengeTagDao: BaseDao<ChallengeTag>{


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: ChallengeTag): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(vararg obj: ChallengeTag): List<Long>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: MutableList<ChallengeTag>): List<Long>

    @Query("DELETE FROM challenge_tag")
    abstract fun deleteAll()
}