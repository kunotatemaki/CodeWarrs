package com.rukiasoft.codewars.network

import android.arch.lifecycle.LiveData
import com.rukiasoft.codewars.network.ApiResponse
import com.rukiasoft.codewars.repository.ChallengeFromServer
import com.rukiasoft.codewars.repository.DetailsFromServer
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

    @GET("/api/v1/users/{username}/code-challenges/authored")
    fun getAuthoredChallenges(@Path("username") userName: String): LiveData<ApiResponse<ChallengeFromServer>>

    @GET("/api/v1/users/{username}/code-challenges/completed")
    fun getCompletedChallenges(@Path("username") userName: String, @Query("page") page: Int): LiveData<ApiResponse<ChallengeFromServer>>

    @GET("/api/v1/code-challenges/{id}")
    fun getDetails(@Path("id") id: String): LiveData<ApiResponse<DetailsFromServer>>


}