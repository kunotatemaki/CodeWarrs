package com.rukiasoft.codewars.ui.search

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.google.gson.Gson
import com.rukiasoft.codewars.persistence.PersistenceManager
import com.rukiasoft.codewars.persistence.entities.UserInfo
import com.rukiasoft.codewars.persistence.relations.UserWithAllInfo
import com.rukiasoft.codewars.repository.UserInfoError
import com.rukiasoft.codewars.repository.UserInfoRequests
import com.rukiasoft.codewars.utils.Constants
import com.rukiasoft.codewars.utils.switchMap
import com.rukiasoft.codewars.vo.Resource
import com.rukiasoft.codewars.vo.Status
import javax.inject.Inject


class SearchViewModel @Inject constructor(private val userInfoRequests: UserInfoRequests,
                                          private val persistenceManager: PersistenceManager) : ViewModel() {

    var animateFab: MutableLiveData<Boolean> = MutableLiveData()
    var searchCardVisible = ObservableBoolean()

    private val query = MutableLiveData<Long>()

    val users: LiveData<List<UserWithAllInfo>>



    var userInfo: MediatorLiveData<Resource<Void>> = MediatorLiveData()

    var usersByDate = true

    var info: LiveData<Resource<UserInfo>>? = null

    init {
        searchCardVisible.set(false)
        animateFab.value = false
        query.value = System.currentTimeMillis()
        users = query.switchMap { _ ->
            getListOfUsers()
        }
    }

    fun search(name: String) {
        info?.let { userInfo.removeSource(info!!) }
        info = userInfoRequests.downloadUserInfo(name, Constants.DEFAULT_NUMBER_OF_RETRIES)
        userInfo.addSource(info!!, {
            it?.let { response ->
                when (response.status) {

                    Status.SUCCESS -> {
                        userInfo.value = Resource.success(null)
                    }
                    Status.ERROR -> {
                        val message = try {
                            val userError: UserInfoError = Gson().fromJson(response.message, UserInfoError::class.java)
                            userError.reason
                        } catch (e: Exception) {
                            ""
                        }
                        userInfo.value = Resource.error(message, null)
                    }
                    Status.LOADING -> {
                        userInfo.value = Resource.loading(null)
                    }
                }
            }
        })
    }

    private fun getListOfUsers(): LiveData<List<UserWithAllInfo>> {
        return if (usersByDate) {
            persistenceManager.getListUsersByDate()
        } else {
            persistenceManager.getListUsersByRank()
        }
    }

    fun getUsersByDate() {
        usersByDate = true
        query.value = System.currentTimeMillis()
    }

    fun getUsersByRank() {
        usersByDate = false
        query.value = System.currentTimeMillis()
    }

}