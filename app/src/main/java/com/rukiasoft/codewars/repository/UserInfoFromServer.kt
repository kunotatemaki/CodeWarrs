package com.rukiasoft.codewars.repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UserInfoFromServer {

    @Expose
    @SerializedName("username")
    var userName: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("honor")
    var honor: Int? = null

    @Expose
    @SerializedName("clan")
    var clan: String? = null

    @Expose
    @SerializedName("leaderboardPosition")
    var leaderBoardPosition: Int? = null

    @Expose
    @SerializedName("skills")
    var skills: List<String>? = null

    @Expose
    @SerializedName("codeChallenges")
    var codeChallenges: CodeChallenge? = null

    @Expose
    @SerializedName("ranks")
    var ranks: Rank? = null


    class CodeChallenge {
        @Expose
        @SerializedName("totalAuthored")
        var totalAuthored: Int? = null

        @Expose
        @SerializedName("totalCompleted")
        var totalCompleted: Int? = null
    }

    class Rank {
        @Expose
        @SerializedName("overall")
        var overall: Item? = null

        @Expose
        @SerializedName("languages")
        var languages: Map<String, Item>? = null
    }


    class Item {
        @Expose
        @SerializedName("rank")
        var rank: Int? = null

        @Expose
        @SerializedName("name")
        var name: String? = null
        @Expose
        @SerializedName("score")
        var score: Int? = null

        @Expose
        @SerializedName("color")
        var color: String? = null
    }

}

