package com.rukiasoft.codewars.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject


class SearchViewModel @Inject constructor(): ViewModel() {

    var animateFab: MutableLiveData<Boolean> = MutableLiveData()
    private var searchState = true

    init {
        animateFab.value = false
    }
    fun getTestText() = "succeed"

    fun isInSearchMode() = searchState
    fun isInSendMode() = searchState.not()
    fun setStateAsSearching(){
        searchState = true
    }
    fun setStateAsToSend(){
        searchState = false
    }
}