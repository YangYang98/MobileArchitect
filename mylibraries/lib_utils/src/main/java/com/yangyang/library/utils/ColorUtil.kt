package com.yangyang.library.utils

import android.graphics.Color
import androidx.annotation.ColorInt


/**
 * Create by Yang Yang on 2023/11/8
 */
object ColorUtil {

    @ColorInt
    fun getTextColor(color: Any): Int {
        return if (color is String) {
            Color.parseColor(color)
        } else {
            color as Int
        }
    }
}