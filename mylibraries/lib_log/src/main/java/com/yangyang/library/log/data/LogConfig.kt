package com.yangyang.library.log.data


/**
 * Create by Yang Yang on 2023/10/27
 */
abstract class LogConfig {

    open fun getGlobalTag(): String {
        return "YangLog"
    }

    open fun enable(): Boolean {
        return true
    }
}