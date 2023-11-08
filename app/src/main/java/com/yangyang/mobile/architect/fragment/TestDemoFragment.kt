package com.yangyang.mobile.architect.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.yangyang.common.ui.BaseFragment
import com.yangyang.mobile.architect.R
import com.yangyang.mobile.architect.demo.LogDemoActivity
import com.yangyang.mobile.architect.demo.TabBottomDemoActivity
import com.yangyang.mobile.architect.demo.TabTopDemoActivity


/**
 * Create by Yang Yang on 2023/11/8
 */
class TestDemoFragment : BaseFragment(), OnClickListener {

    override fun getLayoutId(): Int {
        return R.layout.fragment_test_demo
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getView()?.findViewById<TextView>(R.id.tv_log)?.setOnClickListener(this)
        getView()?.findViewById<TextView>(R.id.tv_tab_bottom)?.setOnClickListener(this)
        getView()?.findViewById<TextView>(R.id.tv_tab_top)?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_log -> {
                startActivity(Intent(context, LogDemoActivity::class.java))
            }
            R.id.tv_tab_bottom -> {
                startActivity(Intent(context, TabBottomDemoActivity::class.java))
            }
            R.id.tv_tab_top -> {
                startActivity(Intent(context, TabTopDemoActivity::class.java))
            }
        }
    }
}