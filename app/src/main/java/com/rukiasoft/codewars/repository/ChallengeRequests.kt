package com.rukiasoft.codewars.repository


import android.arch.lifecycle.LiveData
import com.firefly.studentplanner.network.CodeWarsService
import com.firefly.studentplanner.network.NetworkConstants
import com.rukiasoft.codewars.AppExecutors
import com.rukiasoft.codewars.network.ApiResponse
import com.rukiasoft.codewars.network.CodeWarsServiceFactory
import com.rukiasoft.codewars.network.NetworkBoundResource
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.persistence.utils.PojoToEntities
import com.rukiasoft.codewars.utils.RateLimiter
import com.rukiasoft.codewars.vo.AbsentLiveData
import com.rukiasoft.codewars.vo.Resource
import java.util.*
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
class ChallengeRequests @Inject
constructor(private val codeWarsServiceFactory: CodeWarsServiceFactory,
            private val appExecutors: AppExecutors,
            private val persistenceManager: PersistenceManager
) {

    //request info from user if the last request is more than 15 minutes old
    private val rateLimit: RateLimiter = RateLimiter(15, TimeUnit.MINUTES)

    fun getAuthoredChallenges(userName: String, lastFetched: Date, forceDownload:Boolean, retries: Int): LiveData<Resource<Int>> {
        val host: String = NetworkConstants.API_BASE_URL

        return object : NetworkBoundResource<Int, ChallengeFromServer>(appExecutors) {
            override fun saveCallResult(item: ChallengeFromServer) {

                //store the data in the db
                val challengesToStore = PojoToEntities.getChallengeFromServerResponse(item, userName, true)
                persistenceManager.insertChallenges(challengesToStore)

            }

            override fun shouldFetch(data: Int?): Boolean {
                return data == null || data == 0 || forceDownload || rateLimit.shouldFetch(lastFetched.time)
            }

            override fun loadFromDb(): LiveData<Int> {
                return persistenceManager.getNumberChallengesAuthored(userName)
            }

            override fun createCall(): LiveData<ApiResponse<ChallengeFromServer>> {
                //create call
                val networkService: CodeWarsService? = codeWarsServiceFactory.getCodeWarsService(host, retries)
                return networkService?.getAuthoredChallenges(userName)
                        ?: AbsentLiveData.create()
            }

            override fun onFetchFailed() {
                //something went wrong
            }
        }.asLiveData()
    }

fun getCompletedChallenges(userName: String, lastFetched: Date, page: Int, forceDownload:Boolean, retries: Int): LiveData<Resource<Int>> {
        val host: String = NetworkConstants.API_BASE_URL

        return object : NetworkBoundResource<Int, ChallengeFromServer>(appExecutors) {
            override fun saveCallResult(item: ChallengeFromServer) {

                //store the data in the db
                val challengesToStore = PojoToEntities.getChallengeFromServerResponse(item, userName, false)
                persistenceManager.insertChallenges(challengesToStore)

            }

            override fun shouldFetch(data: Int?): Boolean {
                return data == null || data == 0 || forceDownload || rateLimit.shouldFetch(lastFetched.time)
            }

            override fun loadFromDb(): LiveData<Int> {
                return persistenceManager.getNumberChallengesCompleted(userName)
            }

            override fun createCall(): LiveData<ApiResponse<ChallengeFromServer>> {
                //create call
                val networkService: CodeWarsService? = codeWarsServiceFactory.getCodeWarsService(host, retries)
                return networkService?.getCompletedChallenges(userName, page)
                        ?: AbsentLiveData.create()
            }

            override fun onFetchFailed() {
                //something went wrong
            }
        }.asLiveData()
    }


}