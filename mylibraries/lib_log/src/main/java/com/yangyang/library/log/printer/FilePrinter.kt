package com.yangyang.library.log.printer

import com.yangyang.library.log.bean.LogConfig
import com.yangyang.library.log.bean.LogInfo
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue


/**
 * Create by Yang Yang on 2023/11/1
 */
class FilePrinter private constructor(private val logPath: String, private val retentionTime: Long): ILogPrinter {

    private val writer: LogWriter by lazy { LogWriter() }
    private val worker: PrintWorker by lazy { PrintWorker() }

    companion object {
        @Volatile
        private var instance: FilePrinter? = null

        /**
         *
         * @param logPath log保存路径，如果是外部路径需要确保已经有外部存储的读写权限
         * @param retentionTime log文件的有效时长，单位毫秒，<=0表示一直有效
         */
        fun getInstance(logPath: String, retentionTime: Long) =
            instance ?: synchronized(this) {
                instance ?: FilePrinter(logPath, retentionTime).also { instance = it }
            }

        private val EXECUTOR = Executors.newSingleThreadExecutor()
    }

    init {
        cleanExpiredLog()
    }

    override fun print(config: LogConfig, level: Int, tag: String, printString: String) {
        if (!worker.isRunning()) {
            worker.start()
        }
        worker.put(LogInfo(System.currentTimeMillis(), level, tag, printString))
    }

    private fun cleanExpiredLog() {
        if (retentionTime <= 0) {
            return
        }
        val currentTimeMills = System.currentTimeMillis()
        val logDir = File(logPath)
        val files = logDir.listFiles()
        if (files.isNullOrEmpty()) {
            return
        }
        files.forEach {
            if (currentTimeMills - it.lastModified() > retentionTime) {
                it.delete()
            }
        }
    }

    private fun doPrint(logInfo: LogInfo) {
        val lastFileName = writer.getPreFileName()
        if (lastFileName == null) {
            val newFileName: String = getFileName()
            if (writer.isReady()) {
                writer.close()
            }
            if (!writer.ready(newFileName)) {
                return
            }
        }
        writer.append(logInfo.flattenedLog())
    }

    private fun getFileName(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date(System.currentTimeMillis()))
    }

    private inner class PrintWorker : Runnable {
        val logs = LinkedBlockingQueue<LogInfo>()
        @Volatile
        private var running = false

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        fun put(log: LogInfo) {
            try {
                logs.put(log)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }

        /**
         * 判断工作线程是否还在运行中
         *
         * @return true 在运行
         */
        fun isRunning(): Boolean {
            synchronized(this) {
                return running
            }
        }

        fun start() {
            synchronized(this) {
                EXECUTOR.execute(this)
                running = true
            }
        }

        override fun run() {
            var log: LogInfo
            try {
                while (true) {
                    log = logs.take()
                    doPrint(log)
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                synchronized(this) {
                    running = false
                }
            }
        }
    }

    /**
     * 基于BufferedWriter将log写入文件
     */
    private inner class LogWriter {
        private var preFileName: String? = null
        private var logFile: File? = null
        private var bufferedWriter: BufferedWriter? = null

        fun isReady(): Boolean {
            return bufferedWriter != null
        }

        fun getPreFileName(): String? {
            return preFileName
        }

        /**
         * log写入前的准备操作
         *
         * @param newFileName 要保存log的文件名
         * @return true 表示准备就绪
         */
        fun ready(newFileName: String): Boolean {
            preFileName = newFileName
            logFile = File(logPath, newFileName)

            if (!logFile!!.exists()) {
                try {
                    val parentFile = logFile!!.parentFile
                    if (parentFile != null && !parentFile.exists()) {
                        parentFile.mkdirs()
                    }
                    logFile!!.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                    preFileName = null
                    logFile = null
                    return false
                }
            }
            try {
                bufferedWriter = BufferedWriter(FileWriter(logFile, true))
            } catch (e: Exception) {
                e.printStackTrace()
                preFileName = null
                logFile = null
                return false
            }
            return true
        }

        /**
         * 关闭bufferedWriter
         */
        fun close(): Boolean {
            bufferedWriter?.let {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    return@let false
                } finally {
                    bufferedWriter = null
                    preFileName = null
                    logFile = null
                }
            }
            return true
        }

        /**
         * 将log写入文件
         *
         * @param flattenedLog 格式化后的log
         */
        fun append(flattenedLog: String) {
            try {
                bufferedWriter?.let {
                    it.write(flattenedLog)
                    it.newLine()
                    it.flush()
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}