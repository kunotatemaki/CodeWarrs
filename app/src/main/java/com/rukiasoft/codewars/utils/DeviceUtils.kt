package com.rukiasoft.codewars.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import javax.inject.Inject

/**
 * Created by Robert on 12/04/2016.
 *
 */
@Suppress("unused")
class DeviceUtils @Inject constructor(private val context: Context) {

    fun getNavBarHeight(): Int {
        val result = 0
        val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

        if (!hasMenuKey && !hasBackKey) {
            //The device has a navigation bar
            val resources = context.resources

            val orientation = context.resources.configuration.orientation
            val resourceId: Int
            resourceId = resources.getIdentifier(if (orientation == Configuration.ORIENTATION_PORTRAIT)
                "navigation_bar_height"
            else
                "navigation_bar_width", "dimen", "android")


            if (resourceId > 0) {
                return context.resources.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun isTablet(): Boolean {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun hideKeyboard(view: View?) {
        if (view == null) {
            return
        }
        val imm = view.context.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun isLandscape(): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    fun dipToPixels(dipValue: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
    }

    fun getOffsetForScreenDensity(): Int {
        return when (context.resources.displayMetrics.densityDpi) {
            DisplayMetrics.DENSITY_LOW -> 0
            DisplayMetrics.DENSITY_MEDIUM -> 0
            DisplayMetrics.DENSITY_HIGH -> 0
            DisplayMetrics.DENSITY_XHIGH -> 50
            DisplayMetrics.DENSITY_XXHIGH -> 45
            DisplayMetrics.DENSITY_XXXHIGH -> 45
            DisplayMetrics.DENSITY_TV -> 0
            else -> 0
        }
    }

    fun getScreenWidth(): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun getScreenHeight(): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.y
    }

    fun hasCamera() = context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)

}
