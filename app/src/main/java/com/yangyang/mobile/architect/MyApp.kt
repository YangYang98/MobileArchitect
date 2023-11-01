package com.yangyang.mobile.architect

import android.app.Application
import com.google.gson.Gson
import com.yangyang.library.log.LogManager
import com.yangyang.library.log.bean.LogConfig
import com.yangyang.library.log.printer.ConsolePrinter


/**
 * Create by Yang Yang on 2023/10/30
 */
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        LogManager.init(object : LogConfig() {

            override fun injectJsonParser(): JsonParser {
                return object : JsonParser {
                    override fun toJson(src: Any): String {
                        return Gson().toJson(src)
                    }
                }
            }

            override fun enable(): Boolean {
                return true
            }
        }, ConsolePrinter())
    }
}