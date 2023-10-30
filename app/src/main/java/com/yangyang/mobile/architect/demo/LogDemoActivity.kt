package com.yangyang.mobile.architect.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yangyang.library.log.L
import com.yangyang.mobile.architect.R


/**
 * Create by Yang Yang on 2023/10/30
 */
class LogDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_log_demo)
        findViewById<View>(R.id.btn_log).setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {
        L.a("LogDemoActivity: yangyang")
    }
}