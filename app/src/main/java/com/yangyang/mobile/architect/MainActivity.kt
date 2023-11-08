package com.yangyang.mobile.architect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.yangyang.common.ui.BaseActivity
import com.yangyang.mobile.architect.demo.LogDemoActivity
import com.yangyang.mobile.architect.demo.TabBottomDemoActivity

class MainActivity : BaseActivity(), OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_hilog -> {
                startActivity(Intent(this, LogDemoActivity::class.java))
            }
            R.id.tv_tab_bottom -> {
                startActivity(Intent(this, TabBottomDemoActivity::class.java))
            }
        }
    }
}