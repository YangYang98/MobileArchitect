package com.yangyang.mobile.architect.demo

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yangyang.library.tab.bottom.TabBottomInfo
import com.yangyang.library.tab.bottom.TabBottomLayout
import com.yangyang.library.tab.common.ITabLayout
import com.yangyang.library.utils.DensityUtil
import com.yangyang.mobile.architect.R


/**
 * Create by Yang Yang on 2023/11/8
 */
class TabBottomDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab_bottom_demo)

        initTabBottom()
    }

    private fun initTabBottom() {
        val tabBottomLayout: TabBottomLayout = findViewById<TabBottomLayout?>(R.id.tab_bottom_layout).apply {
            bottomAlpha = 0.85f
        }
        val bottomInfoList = mutableListOf<TabBottomInfo<*>>()
        val homeInfo = TabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoRecommend = TabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            getString(R.string.if_favorite),
            null,
            "#ff656667",
            "#ffd44949"
        )

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background, null)

        val infoCategory = TabBottomInfo<String>(
            "分类",
            bitmap,
            bitmap
        )
        val infoChat = TabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val infoProfile = TabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )

        with(bottomInfoList) {
            add(homeInfo)
            add(infoRecommend)
            add(infoCategory)
            add(infoChat)
            add(infoProfile)
        }
        with(tabBottomLayout) {
            inflateInfo(bottomInfoList)
            addTabSelectedChangeListener(object : ITabLayout.OnTabSelectedListener<TabBottomInfo<*>> {
                override fun onTabSelectedChange(
                    index: Int,
                    prevInfo: TabBottomInfo<*>?,
                    nextInfo: TabBottomInfo<*>
                ) {
                    Toast.makeText(this@TabBottomDemoActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
                }
            })
            defaultSelected(homeInfo)
        }

        val tabBottom = tabBottomLayout.findTab(bottomInfoList[2])?.resetHeight(DensityUtil.dp2px(66f))

    }
}