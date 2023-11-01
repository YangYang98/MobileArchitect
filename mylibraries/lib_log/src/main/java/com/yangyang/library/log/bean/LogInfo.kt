package com.yangyang.library.log.bean

import java.text.SimpleDateFormat
import java.util.Locale


/**
 * Create by Yang Yang on 2023/11/1
 */
data class LogInfo(
    val timeMills: Long,
    val level: Int,
    val tag: String,
    val log: String
) {

    companion object {
        private val sdf = SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA)
    }

    fun flattenedLog(): String {
        return "${getFlattened()}\n$log"
    }

    fun getFlattened(): String {
        return "${format(timeMills)}|$level|$tag|:"
    }

    private fun format(timeMills: Long): String {
        return sdf.format(timeMills)
    }

}