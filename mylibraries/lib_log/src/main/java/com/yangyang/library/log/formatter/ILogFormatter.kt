package com.yangyang.library.log.formatter


/**
 * Create by Yang Yang on 2023/10/30
 */
interface ILogFormatter<T> {

    fun format(data: T): String?
}