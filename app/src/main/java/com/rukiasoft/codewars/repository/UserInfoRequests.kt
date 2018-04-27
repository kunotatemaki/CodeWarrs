package com.rukiasoft.codewars.repository


import android.arch.lifecycle.LiveData
import com.firefly.studentplanner.network.CodeWarsService
import com.firefly.studentplanner.network.NetworkConstants
import com.rukiasoft.codewars.AppExecutors
import com.rukiasoft.codewars.network.ApiResponse
import com.rukiasoft.codewars.network.CodeWarsServiceFactory
import com.rukiasoft.codewars.network.NetworkBoundResource
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.utils.PojoToEntities
import com.rukiasoft.codewars.utils.RateLimiter
import com.rukiasoft.codewars.vo.AbsentLiveData
import com.rukiasoft.codewars.vo.Resource
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Roll on 4/10/17.
 * Repository that handles Task instances.
 *
 * Task - value object name
 * Repository - type of this class.
 */

@Singleton
class UserInfoRequests @Inject
constructor(private val codeWarsServiceFactory: CodeWarsServiceFactory,
            private val appExecutors: AppExecutors,
            private val persistenceManager: PersistenceManager
) {

    //request info from user if the last request is more than 15 minutes old
    private val rateLimit: RateLimiter = RateLimiter(15, TimeUnit.MINUTES)

    fun downloadUserInfo(userName: String, retries: Int): LiveData<Resource<UserInfo>> {
        val host: String = NetworkConstants.API_BASE_URL

        return object : NetworkBoundResource<UserInfo, UserInfoFromServer>(appExecutors) {
            override fun saveCallResult(item: UserInfoFromServer) {

                //store the data in the db
                val user = PojoToEntities.getUserInfoFromServerResponse(item)
                user?.let {
                    persistenceManager.insertUserInfo(user)
                }

            }

            override fun shouldFetch(data: UserInfo?): Boolean {
                return data == null || rateLimit.shouldFetch(data.lastFetchedInfo.time)
            }

            override fun loadFromDb(): LiveData<UserInfo> {
                return persistenceManager.getUserInfo(userName)
            }

            override fun createCall(): LiveData<ApiResponse<UserInfoFromServer>> {
                //create call
                val networkService: CodeWarsService? = codeWarsServiceFactory.getCodeWarsService(host, retries)
                return networkService?.getUserInfo(userName)
                        ?: AbsentLiveData.create()
            }

            override fun onFetchFailed() {
                //something went wrong
            }
        }.asLiveData()
    }


}