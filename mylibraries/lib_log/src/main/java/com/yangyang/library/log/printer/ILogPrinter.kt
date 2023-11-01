package com.yangyang.library.log.printer

import com.yangyang.library.log.bean.LogConfig


/**
 * Create by Yang Yang on 2023/10/30
 */
interface ILogPrinter {

    fun print(config: LogConfig, level: Int, tag: String, printString: String)
}