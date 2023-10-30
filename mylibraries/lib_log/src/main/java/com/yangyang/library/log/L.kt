package com.yangyang.library.log

import android.util.Log
import com.yangyang.library.log.data.LogConfig
import com.yangyang.library.log.data.LogType
import java.lang.StringBuilder


/**
 * Create by Yang Yang on 2023/10/27
 */
class L {

    companion object {

        @JvmStatic
        fun v(vararg contents: Any) {
            log(LogType.V, contents)
        }

        @JvmStatic
        fun vt(tag: String, vararg contents: Any) {
            log(LogType.V, tag, contents)
        }

        @JvmStatic
        fun d(vararg contents: Any) {
            log(LogType.D, contents)
        }

        @JvmStatic
        fun dt(tag: String, vararg contents: Any) {
            log(LogType.D, tag, contents)
        }

        @JvmStatic
        fun i(vararg contents: Any) {
            log(LogType.I, contents)
        }

        @JvmStatic
        fun it(tag: String, vararg contents: Any) {
            log(LogType.I, tag, contents)
        }

        @JvmStatic
        fun w(vararg contents: Any) {
            log(LogType.W, contents)
        }

        @JvmStatic
        fun wt(tag: String, vararg contents: Any) {
            log(LogType.W, tag, contents)
        }

        @JvmStatic
        fun e(vararg contents: Any) {
            log(LogType.E, contents)
        }

        @JvmStatic
        fun et(tag: String, vararg contents: Any) {
            log(LogType.E, tag, contents)
        }

        @JvmStatic
        fun a(vararg contents: Any) {
            log(LogType.A, contents)
        }

        @JvmStatic
        fun at(tag: String, vararg contents: Any) {
            log(LogType.A, tag, contents)
        }

        fun log(@LogType.TYPE type: Int, vararg contents: Any) {
            log(type, LogManager.getInstance().getConfig().getGlobalTag(), contents)
        }

        fun log(@LogType.TYPE type: Int, tag: String, vararg contents: Any) {
            log(LogManager.getInstance().getConfig(), type, tag, contents)
        }

        fun log(config: LogConfig, @LogType.TYPE type: Int, tag: String, vararg contents: Any) {
            if (!config.enable()) {
                return
            }
            val sb = StringBuilder()
            val body = parseBody(contents)
            sb.append(body)
            Log.println(type, tag, body)
        }

        private fun parseBody(contents: Array<out Any>): String {
            val sb = StringBuilder()
            contents.forEach {
                sb.append(it.toString()).append(";")
            }
            if (sb.isNotEmpty()) {
                sb.deleteCharAt(sb.length - 1)
            }
            return sb.toString()
        }

    }
}