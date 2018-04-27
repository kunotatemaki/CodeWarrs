package com.rukiasoft.codewars.repository

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DetailsFromServer {


    @Expose
    @SerializedName("id")
    var id: String? = null

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("category")
    var category: String? = null

    @Expose
    @SerializedName("url")
    var url: String? = null

    @Expose
    @SerializedName("description")
    var description: String? = null



}

