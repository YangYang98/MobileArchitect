package com.yangyang.library.log.utils

import kotlin.math.min


/**
 * Create by Yang Yang on 2023/11/1
 */
class StackTraceUtil {

    companion object {

        fun getCroppedStackTrace(stackTrace: Array<StackTraceElement>, ignorePackage: String?, maxDepth: Int): Array<StackTraceElement> {
            return cropStackTrace(getRealStackTrace(stackTrace, ignorePackage), maxDepth)
        }

        /**
         * 获取除忽略包名以外的堆栈信息
         *
         * @param stackTrace
         * @param ignorePackage
         * @return
         */
        @JvmStatic
        private fun getRealStackTrace(stackTrace: Array<StackTraceElement>, ignorePackage: String?): Array<StackTraceElement> {
            var ignoreDepth = 0
            var className = ""
            for (i in stackTrace.size - 1 downTo  0) {
                className = stackTrace[i].className
                if (!ignorePackage.isNullOrEmpty() && className.startsWith(ignorePackage)) {
                    ignoreDepth = i + 1
                    break
                }
            }
            val realDepth = stackTrace.size - ignoreDepth
            val realStack = Array(realDepth) {
                StackTraceElement("", "", "", 0)
            }
            System.arraycopy(stackTrace, ignoreDepth, realStack, 0, realDepth)
            return realStack
        }

        /**
         * 裁剪堆栈信息
         *
         * @param callStack
         * @param maxDepth
         * @return
         */
        @JvmStatic
        private fun cropStackTrace(callStack: Array<StackTraceElement>, maxDepth: Int): Array<StackTraceElement> {
            var realDepth = callStack.size
            if (maxDepth > 0) {
                realDepth = min(maxDepth, realDepth)
            }
            val realStack = Array(realDepth) {
                StackTraceElement("", "", "", 0)
            }
            System.arraycopy(callStack, 0, realStack, 0, realDepth)
            return realStack
        }
    }
}