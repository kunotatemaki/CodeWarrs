package com.firefly.studentplanner.network


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import com.rukiasoft.codewars.vo.Resource
import com.rukiasoft.codewars.network.ApiResponse

/**
 * Created by Roll on 4/10/17.
 * A generic class that can provide a resource backed by both the sqLite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 * @param <ResultType>
 * @param <RequestType>
 */

abstract class NetworkBoundResourceNoDb<ResultType, RequestType> @MainThread
internal constructor() {

    protected val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        @Suppress("LeakingThis")
        val apiResponse = createCall()
        result.addSource(apiResponse) { response ->

            result.removeSource<ApiResponse<RequestType>>(apiResponse)

            if (response != null && response.isSuccessful()) {
                setResponseInLiveData(response.body)
            } else {
                onFetchFailed()
                result.setValue(Resource.error(response?.errorMessage ?: "", null))

            }
        }
    }


    protected abstract fun onFetchFailed()

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    @MainThread
    protected abstract fun setResponseInLiveData(resultRequest: RequestType?)
}

