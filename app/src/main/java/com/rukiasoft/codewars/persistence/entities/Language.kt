package com.rukiasoft.codewars.persistence.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Roll on 21/11/17.
 * Entity for languages
 */

@Entity(tableName = "language", indices = [(Index(value = arrayOf("user_name", "language_name"), unique = true))])
class Language constructor(@PrimaryKey
                           @ColumnInfo(name = "id")
                           val id: Int?,
                           @ColumnInfo(name = "user_name")
                           val userName: String,
                           @ColumnInfo(name = "language_name")
                           val languageName: String,
                           @ColumnInfo(name = "rank")
                           val rank: Int?,
                           @ColumnInfo(name = "name")
                           val name: String?,
                           @ColumnInfo(name = "color")
                           var color: String?,
                           @ColumnInfo(name = "score")
                           var score: Int?
) {



    fun compareTo(user: Language): Boolean {
        return (userName == user.userName)
                .and(languageName == user.languageName)
                .and(name == user.name)
                .and(rank == user.rank)
                .and(score == user.score)
                .and(color == user.color)
    }

}