package com.yangyang.mobile.architect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.yangyang.library.tab.bottom.TabBottom
import com.yangyang.library.tab.bottom.TabBottomInfo
import com.yangyang.mobile.architect.demo.LogDemoActivity

class MainActivity : AppCompatActivity(), OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeInfo = TabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44849"
        )
        val tabBottom = findViewById<TabBottom>(R.id.tab_bottom).apply {
            setTabInfo(homeInfo)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_hilog -> {
                startActivity(Intent(this, LogDemoActivity::class.java))
            }
        }
    }
}