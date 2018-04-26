package com.rukiasoft.codewars.repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChallengeFromServer {

    @Expose
    @SerializedName("totalPages")
    var totalPages: Int? = null

    @Expose
    @SerializedName("totalItems")
    var totalItems: Int? = null

    @Expose
    @SerializedName("data")
    var data: List<ChallengeItem>? = null


    class ChallengeItem {
        @Expose
        @SerializedName("id")
        var id: String? = null

        @Expose
        @SerializedName("name")
        var name: String? = null

        @Expose
        @SerializedName("slug")
        var slug: String? = null

        @Expose
        @SerializedName("description")
        var description: String? = null

        @Expose
        @SerializedName("rank")
        var rank: Int? = null

        @Expose
        @SerializedName("rankName")
        var rankName: String? = null

        @Expose
        @SerializedName("tags")
        var tags: List<String>? = null

        @Expose
        @SerializedName("languages")
        var languages: List<String>? = null

       @Expose
        @SerializedName("completedLanguages")
        var completedLanguages: List<String>? = null
        @Expose
        @SerializedName("completedAt")
        var completedAt: String? = null
    }
}