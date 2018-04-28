package com.rukiasoft.codewars.persistence.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.rukiasoft.codewars.repository.ChallengeFromServer
import com.rukiasoft.codewars.repository.DetailsFromServer
import com.rukiasoft.codewars.repository.UserInfoFromServer
import com.rukiasoft.codewars.utils.DateUtils
import java.util.*

/**
 * Created by Roll on 21/11/17.
 * Entity for User Info
 */

@Entity(tableName = "detail", indices = [(Index(value = arrayOf("id")))])
class Details constructor(@PrimaryKey
                          @ColumnInfo(name = "id")
                          val id: String,
                          @ColumnInfo(name = "name")
                          val name: String?,
                          @ColumnInfo(name = "category")
                          val category: String?,
                          @ColumnInfo(name = "url")
                          var url: String?,
                          @ColumnInfo(name = "description")
                          var description: String?,
                          @ColumnInfo(name = "last_fetched")
                          var lastFetched: Date
) {

    constructor(details: DetailsFromServer) : this(
            details.id!!, details.name, details.category, details.url, details.description, DateUtils.currentTime)

    fun compareTo(user: Details): Boolean {
        return (id == user.id)
                .and(name == user.name)
                .and(category == user.category)
                .and(url == user.url)
                .and(description == user.description)
    }

    fun getDate() = DateUtils.getDateFormatted(lastFetched)

}