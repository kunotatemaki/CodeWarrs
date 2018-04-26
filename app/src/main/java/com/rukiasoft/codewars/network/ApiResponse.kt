package com.rukiasoft.codewars.network

import android.support.v4.util.ArrayMap
import retrofit2.Response
import java.io.IOException
import java.util.regex.Pattern

/**
 * Created by Roll on 4/10/17.
 * Common class used by API responses.
 * @param <T>
 */

class ApiResponse<T> {
    private val code: Int
    val body: T?
    val errorMessage: String?
    private val links: MutableMap<String, String>

    fun isSuccessful(): Boolean = code in 200..299

    constructor(error: Throwable) {
        code = 500
        body = null
        errorMessage = error.message
        links = mutableMapOf()
    }

    constructor(response: Response<T>) {
        code = response.code()
        if (response.isSuccessful) {
            body = response.body()
            errorMessage = null
        } else {
            var message: String? = null
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody()!!.string()
                } catch (ignored: IOException) {
                    //Timber.e(ignored, "error while parsing response")
                }

            }
            if (message == null || message.trim { it <= ' ' }.isEmpty()) {
                message = response.message()
            }
            errorMessage = message.toString()
            body = null
        }
        val linkHeader = response.headers().get("link")
        if (linkHeader == null) {
            links = mutableMapOf()
        } else {
            links = ArrayMap()
            val matcher = LINK_PATTERN.matcher(linkHeader)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
        }
    }

    companion object {
        private val LINK_PATTERN = Pattern
                .compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
    }
}
