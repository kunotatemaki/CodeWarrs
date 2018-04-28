package com.rukiasoft.codewars.repository


import android.arch.lifecycle.LiveData
import com.firefly.studentplanner.network.CodeWarsService
import com.firefly.studentplanner.network.NetworkConstants
import com.rukiasoft.codewars.AppExecutors
import com.rukiasoft.codewars.network.ApiResponse
import com.rukiasoft.codewars.network.CodeWarsServiceFactory
import com.rukiasoft.codewars.network.NetworkBoundResource
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.persistence.entities.Details
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.utils.PojoToEntities
import com.rukiasoft.codewars.utils.DateUtils
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
class DetailsRequests @Inject
constructor(private val codeWarsServiceFactory: CodeWarsServiceFactory,
            private val appExecutors: AppExecutors,
            private val persistenceManager: PersistenceManager
) {

    //request info from user if the last request is more than 15 minutes old
    private val rateLimit: RateLimiter = RateLimiter(15, TimeUnit.MINUTES)

    fun downloadDetails(id: String, retries: Int): LiveData<Resource<Details>> {
        val host: String = NetworkConstants.API_BASE_URL

        return object : NetworkBoundResource<Details, DetailsFromServer>(appExecutors) {
            override fun saveCallResult(item: DetailsFromServer) {

                //store the data in the db
                val details = PojoToEntities.getDetailsFromServerResponse(item)
                details?.let {
                    persistenceManager.insertDetails(it)
                }

            }

            override fun shouldFetch(data: Details?): Boolean {
                return data == null || rateLimit.shouldFetch(data.lastFetched.time)
            }

            override fun loadFromDb(): LiveData<Details> {
                return persistenceManager.getDetails(id)
            }

            override fun createCall(): LiveData<ApiResponse<DetailsFromServer>> {
                //create call
                val networkService: CodeWarsService? = codeWarsServiceFactory.getCodeWarsService(host, retries)
                return networkService?.getDetails(id)
                        ?: AbsentLiveData.create()
            }

            override fun onFetchFailed() {
                //something went wrong
            }
        }.asLiveData()
    }


}