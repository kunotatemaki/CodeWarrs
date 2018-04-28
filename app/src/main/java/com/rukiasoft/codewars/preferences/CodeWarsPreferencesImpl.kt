package com.rukiasoft.codewars.preferences

import com.rukiasoft.codewars.preferences.CodeWarsPreferencesConstants.DB_VERSION
import com.rukiasoft.codewars.preferences.CodeWarsPreferencesConstants.FETCHED_DATE_USER_INFO
import javax.inject.Inject

/**
 * Created by Raul on 17/11/2017.
 * Implementation of CodeWarsPreferences
 */
class CodeWarsPreferencesImpl @Inject constructor(private var preferencesManager: PreferencesManager) : CodeWarsPreferences {

    override fun clearAllData() {
        preferencesManager.clearAll()
    }

    override fun getFetchDateUserInfo() = preferencesManager.getLongFromPreferences(FETCHED_DATE_USER_INFO)

    override fun setFetchDateUserInfo(value: Long) {
        preferencesManager.setLongIntoPreferences(FETCHED_DATE_USER_INFO, value)
    }

    override fun deleteFetchDateUserInfo() {
        preferencesManager.deleteVarFromSharedPreferences(FETCHED_DATE_USER_INFO)
    }

    override fun getDbVersion() = preferencesManager.getIntFromPreferences(DB_VERSION)

    override fun setDbVersion(value: Int) {
        preferencesManager.setIntIntoPreferences(DB_VERSION, value)
    }

    override fun deleteDbVersion() {
        preferencesManager.deleteVarFromSharedPreferences(DB_VERSION)
    }

}
