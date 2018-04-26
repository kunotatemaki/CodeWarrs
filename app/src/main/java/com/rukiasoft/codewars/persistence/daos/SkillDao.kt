package com.rukiasoft.codewars.persistence.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rukiasoft.codewars.persistence.entities.Skill
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.utils.getDistinct

@Dao
abstract class SkillDao: BaseDao<Skill>{


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: Skill): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(vararg obj: Skill): List<Long>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: MutableList<Skill>): List<Long>

    @Query("DELETE FROM skill")
    abstract fun deleteAll()
}