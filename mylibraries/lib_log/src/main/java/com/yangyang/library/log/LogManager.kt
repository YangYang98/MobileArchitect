package com.yangyang.library.log

import com.yangyang.library.log.bean.LogConfig
import com.yangyang.library.log.printer.ILogPrinter
import java.lang.NullPointerException


/**
 * Create by Yang Yang on 2023/10/27
 */
class LogManager private constructor(
    private val config: LogConfig,
    private var printers: MutableList<ILogPrinter>
) {

    companion object {
        private var instance: LogManager? = null

        fun getInstance(): LogManager = instance ?: throw NullPointerException("LogManager未初始化")

        @Synchronized
        @JvmStatic
        fun init(config: LogConfig, vararg printers: ILogPrinter) {
            instance = LogManager(config, printers.toMutableList())
        }
    }

    fun getConfig(): LogConfig {
        return config
    }

    fun getPrinters(): List<ILogPrinter> {
        return printers
    }

    fun addPrinter(printer: ILogPrinter) {
        printers.add(printer)
    }

    fun removePrinter(printer: ILogPrinter) {
        printers.remove(printer)
    }

}