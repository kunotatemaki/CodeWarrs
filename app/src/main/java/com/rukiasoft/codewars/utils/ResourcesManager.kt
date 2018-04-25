package com.rukiasoft.codewars.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Raul on 17/11/2017.
 * Implementation of @ResourcesManager in Android
 */
@Singleton
class ResourcesManager @Inject constructor(val context: Context){

    fun getString(resId: Int): String {
        return context.getString(resId)
    }

    fun getColor(resId: Int): Int {
        return ResourcesCompat.getColor(context.resources, resId, null)
    }

    fun getInteger(resId: Int): Int {
        return context.resources.getInteger(resId)
    }

    fun getResources(): Resources {
        return context.resources
    }

    fun getDpFromSp(dimensionInDp: Int): Int {
        return context.resources.getDimensionPixelSize(dimensionInDp)
    }

    fun getDimension(resId: Int): Int {
        return context.resources.getDimension(resId).toInt()
    }

    fun getDrawable(resId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resId)
    }

    fun getResourceIdFromName(resourceName: String): Int{
        return context.resources.getIdentifier(resourceName, "id", context.packageName)
    }

}