package com.yangyang.library.log

import com.yangyang.library.log.data.LogConfig
import java.lang.NullPointerException


/**
 * Create by Yang Yang on 2023/10/27
 */
class LogManager private constructor(private val config: LogConfig) {

    companion object {
        private var instance: LogManager? = null

        fun getInstance(): LogManager = instance ?: throw NullPointerException("LogManager未初始化")

        @Synchronized
        @JvmStatic
        fun init(config: LogConfig) {
            instance = LogManager(config)
        }
    }

    fun getConfig(): LogConfig {
        return config
    }

}