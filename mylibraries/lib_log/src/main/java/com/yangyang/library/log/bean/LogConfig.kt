package com.yangyang.library.log.bean

import com.yangyang.library.log.formatter.StackTraceFormatter
import com.yangyang.library.log.formatter.ThreadFormatter
import com.yangyang.library.log.printer.ILogPrinter


/**
 * Create by Yang Yang on 2023/10/27
 */
abstract class LogConfig {

    companion object {
        val MAX_LEN = 512

        val THREAD_FORMATTER = ThreadFormatter()
        val STACK_TRACE_FORMATTER = StackTraceFormatter()
    }

    open fun getGlobalTag(): String {
        return "YangLog"
    }

    open fun enable(): Boolean {
        return true
    }

    open fun includeThread(): Boolean {
        return false
    }

    open fun stackTraceDepth(): Int {
        return 5
    }

    fun printers(): Array<ILogPrinter>? {
        return null
    }

    interface JsonParser {
        fun toJson(src: Any): String
    }

    open fun injectJsonParser(): JsonParser? {
        return null
    }
}