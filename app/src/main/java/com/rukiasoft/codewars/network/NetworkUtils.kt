package com.firefly.studentplanner.network

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject

/**
 * Created by Raul Feliz on 18/11/2017.
 * Utils to get info from the device's connectivity
 */
class NetworkUtils @Inject constructor(val context: Context){

    fun isNetworkAvailable(): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }


}
