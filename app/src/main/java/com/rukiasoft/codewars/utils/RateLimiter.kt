package com.rukiasoft.codewars.utils


import java.util.concurrent.TimeUnit

/**
 * Created by Roll on 4/10/17.
 *
 * Utility class that decides whether we should fetch some data or not.
 */

class RateLimiter(timeout: Int, timeUnit: TimeUnit) {
    //private val timestamps = ArrayMap<KEY, Long>()
    private val timeout: Long = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(lastFetched: Long): Boolean {
        //val lastFetched = timestamps[key]
        val now = now()
        if (lastFetched == 0L) {
            return true
        }
        if (now - lastFetched > timeout) {
            return true
        }
        return false
    }

    private fun now(): Long {
        return System.currentTimeMillis()
    }

    /*@Synchronized
    fun reset(key: KEY) {
        timestamps.remove(key)
    }*/
}
