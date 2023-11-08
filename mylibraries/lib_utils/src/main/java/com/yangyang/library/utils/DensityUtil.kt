package com.yangyang.library.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.view.WindowManager


/**
 * Create by Yang Yang on 2023/11/1
 */
object DensityUtil {
    /**
     * dip --> px
     */
    fun dp2px(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    /**
     * px --> dp
     */
    fun px2dp(px: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (px / density + 0.5f).toInt()
    }

    /**
     * sp --> px
     */
    fun sp2px(sp: Float): Int {
        val sd = Resources.getSystem().displayMetrics.scaledDensity
        return (sp * sd + 0.5f).toInt()
    }

    /**
     * px --> sp
     */
    fun px2sp(px: Float): Int {
        val sd = Resources.getSystem().displayMetrics.scaledDensity
        return (px / sd + 0.5f).toInt()
    }

    fun getDisplayWidthInPx(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        if (wm != null) {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val currentWindowMetrics = wm.currentWindowMetrics
                val bounds = currentWindowMetrics.bounds
                bounds.width()
            } else {
                val display = wm.defaultDisplay
                val size = Point()
                display.getSize(size)
                size.x
            }
        }
        return 0
    }

    fun getDisplayHeightInPx(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        if (wm != null) {
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size.y
        }
        return 0
    }

    fun getStatusBarDimensionPx(): Int {
        var statusBarHeight = 0
        val res: Resources = Resources.getSystem()
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }
}