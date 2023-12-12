package com.yangyang.mobile.architect

import com.google.gson.Gson
import com.yangyang.common.ui.BaseApplication
import com.yangyang.library.log.LogManager
import com.yangyang.library.log.bean.LogConfig
import com.yangyang.library.log.printer.ConsolePrinter
import com.yangyang.library.log.printer.FilePrinter
import com.yangyang.library.utils.ActivityManager


/**
 * Create by Yang Yang on 2023/10/30
 */
class MyApp : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        LogManager.init(
            object : LogConfig() {
                override fun injectJsonParser(): JsonParser? {
                    return object : JsonParser {
                        override fun toJson(src: Any): String {
                            return Gson().toJson(src)
                        }
                    }
                }

                override fun enable(): Boolean {
                    return true
                }

                override fun stackTraceDepth(): Int {
                    return 0
                }
            },
            ConsolePrinter(),
            FilePrinter.getInstance(applicationContext.cacheDir.absolutePath, 0)
        )

        ActivityManager.instance.init(this)
    }
}