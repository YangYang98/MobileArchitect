package com.yangyang.library.log.data

import android.util.Log
import androidx.annotation.IntDef


/**
 * Create by Yang Yang on 2023/10/27
 */
class LogType {

    companion object {
        const val V = Log.VERBOSE
        const val D = Log.DEBUG
        const val I = Log.INFO
        const val W = Log.WARN
        const val E = Log.ERROR
        const val A = Log.ASSERT
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(V, D, I, W, E, A)
    annotation class TYPE
}