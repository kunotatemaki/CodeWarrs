package com.rukiasoft.codewars.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.firefly.studentplanner.network.CodeWarsService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Roll on 5/10/17.
 * factory method to return the {@link CodeWarsService} class
 */
@Singleton
class CodeWarsServiceFactory @Inject constructor() {

    fun getCodeWarsService(host: String, retries: Int): CodeWarsService? {
        // the base url has to end with "/" or retrofit will crash
        var baseUrl: String = host

        if (!baseUrl.endsWith("/")) {
            baseUrl += "/"
        }

        var codeWarsService: CodeWarsService? = null

        val client = OkHttpClient().newBuilder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()

        try {
            codeWarsService = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory(retries))
                    .build()
                    .create<CodeWarsService>(CodeWarsService::class.java)

        } catch (e: IllegalArgumentException) {

        }

        return codeWarsService

    }


}
