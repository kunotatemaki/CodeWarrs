package com.rukiasoft.codewars.persistence.entities

import android.arch.persistence.room.*

/**
 * Created by Roll on 21/11/17.
 * Entity for skills
 */

@Entity(tableName = "skill", indices = [(Index(value = arrayOf("user_name", "skill"), unique = true))])
class Skill constructor(@PrimaryKey(autoGenerate = true)
                        @ColumnInfo(name = "id")
                        val id: Int?,
                        @ColumnInfo(name = "skill")
                        val skill: String,
                        @ColumnInfo(name = "user_name")
                        val userName: String
) {


    fun compareTo(user: Skill): Boolean {
        return (userName == user.userName)
                .and(userName == user.userName)
    }

}