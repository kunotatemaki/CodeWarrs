package com.rukiasoft.codewars.persistence.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.rukiasoft.codewars.repository.UserInfoFromServer
import java.util.*

/**
 * Created by Roll on 21/11/17.
 * Entity for User Info
 */

@Entity(tableName = "user_info", indices = [(Index(value = arrayOf("last_fetched", "overall_score")))])
class UserInfo constructor(@PrimaryKey
                           @ColumnInfo(name = "user_name")
                           val userName: String,
                           @ColumnInfo(name = "name")
                           val name: String?,
                           @ColumnInfo(name = "honor")
                           val honor: Int?,
                           @ColumnInfo(name = "clan")
                           val clan: String?,
                           @ColumnInfo(name = "leader_board_position")
                           var leaderBoardPosition: Int?,
                           @ColumnInfo(name = "overall_rank")
                           var overallRank: Int?,
                           @ColumnInfo(name = "overall_name")
                           var overallName: String?,
                           @ColumnInfo(name = "overall_color")
                           var overallColor: String?,
                           @ColumnInfo(name = "overall_score")
                           var overallScore: Int?,
                           @ColumnInfo(name = "last_fetched")
                           var lastFetched: Date
) {

    constructor(user: UserInfoFromServer, date: Date) : this(
            user.userName!!, user.name, user.honor, user.clan, user.leaderBoardPosition,
            user.ranks?.overall?.rank, user.ranks?.overall?.name, user.ranks?.overall?.color,
            user.ranks?.overall?.score, date)

    fun compareTo(user: UserInfo): Boolean {
        return (userName == user.userName)
                .and(honor == user.honor)
                .and(name == user.name)
                .and(clan == user.clan)
                .and(leaderBoardPosition == user.leaderBoardPosition)
                .and(lastFetched == user.lastFetched)
                .and(overallRank == user.overallRank)
                .and(overallName == user.overallName)
                .and(overallColor == user.overallColor)
                .and(overallScore == user.overallScore)
    }

}