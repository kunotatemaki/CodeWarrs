package com.rukiasoft.codewars.ui.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import javax.inject.Inject


class SearchViewModel @Inject constructor(): ViewModel() {

    var animateFab: MutableLiveData<Boolean> = MutableLiveData()
    var searchCardVisible = ObservableBoolean()

    init {
        searchCardVisible.set(false)
        animateFab.value = false
    }


}