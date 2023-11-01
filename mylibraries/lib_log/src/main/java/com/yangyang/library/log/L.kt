package com.yangyang.library.log

import com.yangyang.library.log.bean.LogConfig
import com.yangyang.library.log.bean.LogType
import com.yangyang.library.log.printer.ILogPrinter
import com.yangyang.library.log.utils.StackTraceUtil


/**
 * Create by Yang Yang on 2023/10/27
 */
class L {

    companion object {

        private val className = L::class.java.name
        private val LOG_PACKAGE: String = className.substring(0, className.lastIndexOf('.') + 1)

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
            if (config.includeThread()) {
                val threadInfo = LogConfig.THREAD_FORMATTER.format(Thread.currentThread())
                sb.append(threadInfo).append("\n")
            }
            if (config.stackTraceDepth() > 0) {
                val stackTrace = LogConfig.STACK_TRACE_FORMATTER.format(
                    StackTraceUtil.getCroppedStackTrace(Throwable().stackTrace, LOG_PACKAGE, config.stackTraceDepth())
                )
                sb.append(stackTrace).append("\n")
            }
            val body = parseBody(contents, config)
            sb.append(body)
            val printers: List<ILogPrinter> = if (!config.printers().isNullOrEmpty()) config.printers()!!.asList() else LogManager.getInstance().getPrinters()

            printers.forEach {
                it.print(config, type, tag, sb.toString())
            }
        }

        private fun parseBody(contents: Array<out Any>, config: LogConfig): String {
            if (config.injectJsonParser() != null) {
                return config.injectJsonParser()!!.toJson(contents)
            }
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