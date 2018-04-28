package com.rukiasoft.codewars.persistence.daos

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.rukiasoft.codewars.persistence.entities.Details
import com.rukiasoft.codewars.utils.getDistinct

@Dao
abstract class DetailsDao: BaseDao<Details>{

    @Query("SELECT * FROM detail WHERE id = :id")
    protected abstract fun getDetailsInternal(id: String): LiveData<Details>

    fun getDetails(id: String): LiveData<Details> =
            getDetailsInternal(id).getDistinct()

    @Query("DELETE FROM detail")
    abstract fun deleteAll()
}