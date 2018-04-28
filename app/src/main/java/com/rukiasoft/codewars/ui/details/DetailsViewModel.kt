package com.rukiasoft.codewars.ui.details

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.rukiasoft.codewars.persistence.entities.Details
import com.rukiasoft.codewars.repository.DetailsRequests
import com.rukiasoft.codewars.utils.Constants
import com.rukiasoft.codewars.utils.switchMap
import com.rukiasoft.codewars.vo.AbsentLiveData
import com.rukiasoft.codewars.vo.Resource
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val detailsRequests: DetailsRequests) : ViewModel() {

    private val query = MutableLiveData<Long>()

    private var id = ""

    val details: LiveData<Resource<Details>>

    init {

        details = query.switchMap { _ ->
            if (id.isNotBlank()) {
                detailsRequests.downloadDetails(id, Constants.DEFAULT_NUMBER_OF_RETRIES)
            } else {
                AbsentLiveData.create()
            }
        }
    }

    fun setId(id: String){
        this.id = id
        query.value = System.currentTimeMillis()
    }
}