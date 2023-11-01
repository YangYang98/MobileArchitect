package com.yangyang.library.utils

import android.content.res.Resources


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
}