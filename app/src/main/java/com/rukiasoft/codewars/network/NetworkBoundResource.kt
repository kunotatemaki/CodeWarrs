package com.rukiasoft.codewars.network


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.rukiasoft.codewars.AppExecutors
import com.rukiasoft.codewars.vo.Resource

/**
 * Created by Roll on 4/10/17.
 * A generic class that can provide a resource backed by both the sqLite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <ResultType>
 * @param <RequestType>
 */

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
internal constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                result.value = Resource.success(data)
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) {
                    newData -> result.value = Resource.success(newData) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource) {
            newData -> result.setValue(Resource.loading(newData))
        }
        result.addSource<ApiResponse<RequestType>>(apiResponse) { response ->
            result.removeSource<ApiResponse<RequestType>>(apiResponse)
            result.removeSource(dbSource)

            if (response?.isSuccessful()!!) {
                appExecutors.diskIO().execute({
                    saveCallResult(processResponse(response))
                    appExecutors.mainThread().execute({
                        // we specially request a new live data,
                        // otherwise we will get immediately last cached value,
                        // which may not be updated with latest results received from network.
                        result.addSource(loadFromDb()) {
                            newData -> result.setValue(Resource.success(newData))
                        }
                    }
                    )
                })
            } else {
                onFetchFailed()
                result.addSource(dbSource
                ) {
                    newData -> result.setValue(Resource.error(response.errorMessage?:"", newData))
                }
            }
        }
    }

    protected abstract fun onFetchFailed()

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @Suppress("MemberVisibilityCanPrivate")
    @WorkerThread
    protected fun processResponse(response: ApiResponse<RequestType>): RequestType {
        return response.body!!
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @WorkerThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}

