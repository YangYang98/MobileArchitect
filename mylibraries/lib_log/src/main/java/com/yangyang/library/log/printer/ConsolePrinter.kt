package com.yangyang.library.log.printer

import android.util.Log
import com.yangyang.library.log.bean.LogConfig
import com.yangyang.library.log.bean.LogConfig.Companion.MAX_LEN
import java.lang.StringBuilder


/**
 * Create by Yang Yang on 2023/10/31
 */
class ConsolePrinter : ILogPrinter {

    override fun print(config: LogConfig, level: Int, tag: String, printString: String) {
        val len = printString.length
        val countOfSub = len / MAX_LEN
        if (countOfSub > 0) {
            val log = StringBuilder()
            var index = 0
            for (i in 0 until countOfSub) {
                log.append(printString.substring(index, index + MAX_LEN))
                index += MAX_LEN
            }
            if (index != len) {
                log.append(printString.substring(index, len))
            }
            Log.println(level, tag, log.toString())
        } else {
            Log.println(level, tag, printString)
        }
    }
}