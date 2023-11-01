package com.yangyang.library.log.formatter

import java.lang.StringBuilder


/**
 * Create by Yang Yang on 2023/10/30
 */
class StackTraceFormatter : ILogFormatter<Array<StackTraceElement>?> {

    override fun format(stackTrace: Array<StackTraceElement>?): String? {
        val sb = StringBuilder(128)
        if (stackTrace.isNullOrEmpty()) {
            return null
        } else if (stackTrace.size == 1) {
            return "\t- ${stackTrace[0]}"
        } else {
            stackTrace.forEachIndexed { index, stackTraceElement ->
                if (index == 0) {
                    sb.append("stackTrace: \n")
                }
                if (index != stackTrace.size - 1) {
                    sb.append("\t├  ")
                    sb.append(stackTraceElement.toString())
                    sb.append("\n")
                } else {
                    sb.append("\t└  ")
                    sb.append(stackTraceElement.toString())
                }
            }
            return sb.toString()
        }
    }
}