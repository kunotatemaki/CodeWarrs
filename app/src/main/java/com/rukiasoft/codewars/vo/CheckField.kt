package com.rukiasoft.codewars.vo

import android.support.annotation.NonNull
import android.support.annotation.Nullable

/**
 * Created by Roll on 7/10/17.
 * A generic class that holds a value of a field with it validated status2.
 * @param <T>
 */
class CheckField<out T>(@NonNull val valid: Boolean, @Nullable val data: T?, @Nullable val message: String?) {


    companion object {

        fun <T> invalid(data: T, msg: String): CheckField<T> {
            return CheckField(false, data, msg)
        }

        fun <T> valid(data: T?): CheckField<T> {
            return CheckField(true, data, null)
        }

    }
}
