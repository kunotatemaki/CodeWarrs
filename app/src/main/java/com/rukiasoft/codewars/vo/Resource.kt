package com.rukiasoft.codewars.vo

import android.support.annotation.NonNull
import android.support.annotation.Nullable


/**
 * Created by Roll on 4/10/17.
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
class Resource<out T>(@NonNull val status: Status, @Nullable val data: T?, @Nullable val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
