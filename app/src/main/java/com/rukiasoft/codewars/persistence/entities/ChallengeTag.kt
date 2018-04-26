package com.rukiasoft.codewars.persistence.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Roll on 21/11/17.
 * Entity for challenge language completed
 */

@Entity(tableName = "challenge_tag", indices = [(Index(value = arrayOf("challenge_id", "name"), unique = true))])
class ChallengeTag constructor(@PrimaryKey(autoGenerate = true)
                                             @ColumnInfo(name = "id")
                                             val id: Int?,
                               @ColumnInfo(name = "name")
                                             val name: String,
                               @ColumnInfo(name = "challenge_id")
                                             val challengeId: String
) {


    fun compareTo(user: ChallengeTag): Boolean {
        return (name == user.name)
                .and(challengeId == user.challengeId)
    }

}