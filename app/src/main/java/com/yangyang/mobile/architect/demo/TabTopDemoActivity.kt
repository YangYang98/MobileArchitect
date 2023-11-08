package com.yangyang.mobile.architect.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yangyang.library.tab.common.ITabLayout
import com.yangyang.library.tab.top.TabTopInfo
import com.yangyang.library.tab.top.TabTopLayout
import com.yangyang.mobile.architect.R


/**
 * Create by Yang Yang on 2023/11/8
 */
class TabTopDemoActivity : AppCompatActivity() {

    var tabsStr = arrayOf(
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "装修",
        "运动"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab_top_demo)

        initTabTop()
    }

    private fun initTabTop() {
        val tabTobLayout = findViewById<TabTopLayout>(R.id.tab_top_layout)
        val infoList = mutableListOf<TabTopInfo<*>>()
        val defaultColor = resources.getColor(R.color.tabBottomDefaultColor)
        val tintColor = resources.getColor(R.color.tabBottomTintColor)

        tabsStr.forEach {
            val info = TabTopInfo(it, defaultColor, tintColor)
            infoList.add(info)
        }

        with(tabTobLayout) {
            inflateInfo(infoList)
            addTabSelectedChangeListener(object : ITabLayout.OnTabSelectedListener<TabTopInfo<*>> {
                override fun onTabSelectedChange(
                    index: Int,
                    prevInfo: TabTopInfo<*>?,
                    nextInfo: TabTopInfo<*>
                ) {
                    Toast.makeText(this@TabTopDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
                }
            })
            defaultSelected(infoList[0])
        }
    }
}