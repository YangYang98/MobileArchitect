package com.yangyang.mobile.architect.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yangyang.library.log.L
import com.yangyang.library.log.LogManager
import com.yangyang.library.log.bean.LogConfig
import com.yangyang.library.log.bean.LogType
import com.yangyang.library.log.printer.ViewPrinter
import com.yangyang.mobile.architect.R


/**
 * Create by Yang Yang on 2023/10/30
 */
class LogDemoActivity : AppCompatActivity() {

    private val viewPrinter: ViewPrinter by lazy { ViewPrinter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_log_demo)
        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
        }

        viewPrinter.getViewProvider().showFloatingView()
        LogManager.getInstance().addPrinter(viewPrinter)
    }

    private fun printLog() {
        L.log(object : LogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, LogType.E, "LogDemoActivity:", " yangyang")
        L.e("LogDemoActivity: yangyang")
    }
}