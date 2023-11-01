package com.yangyang.library.log.formatter


/**
 * Create by Yang Yang on 2023/10/30
 */
class ThreadFormatter : ILogFormatter<Thread> {

    override fun format(data: Thread): String {
        return "Thread:${data.name}"
    }
}