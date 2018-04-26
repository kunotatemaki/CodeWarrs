package com.rukiasoft.codewars.persistence.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.rukiasoft.codewars.persistence.entities.Language

@Dao
abstract class LanguageDao: BaseDao<Language>{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: Language): Long


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(vararg obj: Language): List<Long>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override fun insert(obj: MutableList<Language>): List<Long>

    @Query("DELETE FROM user_info")
    abstract fun deleteAll()
}