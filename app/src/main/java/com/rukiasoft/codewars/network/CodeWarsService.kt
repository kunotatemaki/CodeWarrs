package com.firefly.studentplanner.network

import android.arch.lifecycle.LiveData
import com.rukiasoft.codewars.network.ApiResponse
import com.rukiasoft.codewars.repository.UserInfoFromServer
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 * Created by Roll
 * Requests to firefly service
 *
 */
interface CodeWarsService {

    @GET("/api/v1/users/{username}")
    fun getUserInfo(@Path("username") userName: String): LiveData<ApiResponse<UserInfoFromServer>>



}