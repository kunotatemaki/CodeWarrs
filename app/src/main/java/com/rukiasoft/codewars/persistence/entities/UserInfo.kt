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

@Entity(tableName = "user_info", indices = [(Index(value = arrayOf("last_fetched_info", "overall_score")))])
class UserInfo constructor(@PrimaryKey
                           @ColumnInfo(name = "user_name")
                           var userName: String,
                           @ColumnInfo(name = "name")
                           var name: String?,
                           @ColumnInfo(name = "honor")
                           var honor: Int?,
                           @ColumnInfo(name = "clan")
                           var clan: String?,
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
                           @ColumnInfo(name = "last_fetched_info")
                           var lastFetchedInfo: Date,
                           @ColumnInfo(name = "last_fetched_authored")
                           var lastFetchedAuthored: Date?,
                           @ColumnInfo(name = "last_fetched_completed")
                           var lastFetchedCompleted: Date?,
                           @ColumnInfo(name = "n_page_completed")
                           var nPageCompleted: Int?,
                           @ColumnInfo(name = "n_items_completed")
                           var nItemsCompleted: Int?,
                           @ColumnInfo(name = "last_page_downloaded")
                           var lastPageDownloaded: Int

) {

    constructor(user: UserInfoFromServer, date: Date) : this(
            user.userName!!, user.name, user.honor, user.clan, user.leaderBoardPosition,
            user.ranks?.overall?.rank, user.ranks?.overall?.name, user.ranks?.overall?.color,
            user.ranks?.overall?.score, date, null, null,
            null, null, 0)

    fun updateUserInfo(user: UserInfo) {
        userName = user.userName
        name = user.name
        honor = user.honor
        clan = user.clan
        leaderBoardPosition = user.leaderBoardPosition
        overallRank = user.overallRank
        overallName = user.overallName
        overallColor = user.overallColor
        overallScore = user.overallScore
        lastFetchedInfo = user.lastFetchedInfo
    }

    fun compareTo(user: UserInfo): Boolean {
        return (userName == user.userName)
                .and(honor == user.honor)
                .and(name == user.name)
                .and(clan == user.clan)
                .and(leaderBoardPosition == user.leaderBoardPosition)
                .and(lastFetchedInfo == user.lastFetchedInfo)
                .and(overallRank == user.overallRank)
                .and(overallName == user.overallName)
                .and(overallColor == user.overallColor)
                .and(lastFetchedAuthored == user.lastFetchedAuthored)
                .and(lastFetchedCompleted == user.lastFetchedCompleted)
                .and(nItemsCompleted == user.nItemsCompleted)
                .and(nPageCompleted == user.nPageCompleted)
                .and(overallScore == user.overallScore)
                .and(lastPageDownloaded == user.lastPageDownloaded)
    }

}