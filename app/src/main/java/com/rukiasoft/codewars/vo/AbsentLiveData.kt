package com.rukiasoft.codewars.vo


import android.arch.lifecycle.LiveData

/**
 * Created by Raul Feliz on 4/10/17.
 * * A LiveData class that has {@code null} value.
 */

class AbsentLiveData<T> private constructor() : LiveData<T>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}
