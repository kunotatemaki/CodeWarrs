package com.rukiasoft.codewars.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import java.lang.ref.WeakReference

/***
 * this function allows to execute some function only if the value of weak reference is not null
 * to avoid checking if not null, or having to use ?
 */
fun <T> WeakReference<T>.safe(body : T.() -> Unit) {
    this.get()?.body()
}

fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>)
        = Transformations.switchMap(this, func)!!
/*fun <X, Y> LiveData<X>.switchMap(func: (X) -> LiveData<Y>)
        = Transformations.switchMap(this, func)*/


fun <T> LiveData<T>.getDistinct(): LiveData<T> {
    val distinctLiveData = MediatorLiveData<T>()
    distinctLiveData.addSource(this, object : Observer<T> {
        private var initialized = false
        private var lastObj: T? = null
        override fun onChanged(obj: T?) {
            if (!initialized) {
                initialized = true
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            } else if ((obj == null && lastObj != null)
                    || obj != lastObj) {
                lastObj = obj
                distinctLiveData.postValue(lastObj)
            }
        }
    })
    return distinctLiveData
}