package com.firefly.studentplanner.utils.preferences

/**
 * Created by Raul on 17/11/2017.
 * Class for accessing the shared preferences in the app
 */
interface CodeWarsPreferences {

    fun getFetchDateUserInfo(): Long
    fun setFetchDateUserInfo(value: Long)
    fun deleteFetchDateUserInfo()

    fun getDbVersion(): Int
    fun setDbVersion(value: Int)
    fun deleteDbVersion()

    fun clearAllData()
}