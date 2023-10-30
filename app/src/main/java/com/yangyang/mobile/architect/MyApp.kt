package com.yangyang.mobile.architect

import android.app.Application
import com.yangyang.library.log.LogManager
import com.yangyang.library.log.data.LogConfig


/**
 * Create by Yang Yang on 2023/10/30
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        LogManager.init(object : LogConfig() {

            override fun enable(): Boolean {
                return true
            }
        })
    }
}