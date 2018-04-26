package com.rukiasoft.codewars.persistence.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.rukiasoft.codewars.repository.ChallengeFromServer
import com.rukiasoft.codewars.repository.UserInfoFromServer
import com.rukiasoft.codewars.utils.DateUtils
import java.util.*

/**
 * Created by Roll on 21/11/17.
 * Entity for User Info
 */

@Entity(tableName = "challenge", indices = [(Index(value = arrayOf("user_name")))])
class Challenge constructor(@PrimaryKey
                            @ColumnInfo(name = "id")
                            val id: String,
                            @ColumnInfo(name = "name")
                            val name: String?,
                            @ColumnInfo(name = "slug")
                            val slug: String?,
                            @ColumnInfo(name = "completed_at")
                            val completedAt: Date?,
                            @ColumnInfo(name = "user_name")
                            var userName: String?,
                            @ColumnInfo(name = "description")
                            var description: String?,
                            @ColumnInfo(name = "rank")
                            var rank: Int?,
                            @ColumnInfo(name = "rankName")
                            var rankName: String?,
                            @ColumnInfo(name = "authored")
                            var authored: Boolean?
) {

    constructor(challenge: ChallengeFromServer.ChallengeItem, userName:String, authored: Boolean) : this(
            challenge.id!!, challenge.name, challenge.slug, DateUtils.parseIsoDate(challenge.completedAt), userName,
            challenge.description, challenge.rank, challenge.rankName,
            authored)

    fun compareTo(user: Challenge): Boolean {
        return (userName == user.userName)
                .and(id == user.id)
                .and(name == user.name)
                .and(slug == user.slug)
                .and(completedAt == user.completedAt)
                .and(description == user.description)
                .and(rank == user.rank)
                .and(authored == user.authored)
                .and(rankName == user.rankName)
    }

}