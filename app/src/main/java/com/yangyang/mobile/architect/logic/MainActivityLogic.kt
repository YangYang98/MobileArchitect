package com.yangyang.mobile.architect.logic

import android.content.res.Resources
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager
import com.yangyang.common.ui.tab.FragmentTabView
import com.yangyang.common.ui.tab.TabViewAdapter
import com.yangyang.library.tab.bottom.TabBottomInfo
import com.yangyang.library.tab.bottom.TabBottomLayout
import com.yangyang.library.tab.common.ITabLayout
import com.yangyang.mobile.architect.R
import com.yangyang.mobile.architect.fragment.CategoryFragment
import com.yangyang.mobile.architect.fragment.FavoriteFragment
import com.yangyang.mobile.architect.fragment.HomePageFragment
import com.yangyang.mobile.architect.fragment.ProfileFragment
import com.yangyang.mobile.architect.fragment.RecommendFragment


/**
 * Create by Yang Yang on 2023/11/8
 */
class MainActivityLogic(private val activityProvider: ActivityProvider) {

    lateinit var fragmentTabView: FragmentTabView
        private set
    lateinit var tabBottomLayout: TabBottomLayout
        private set
    private val infoList = mutableListOf<TabBottomInfo<*>>()
    private var currentItemIndex: Int = 0

    init {
        initTabBottom()
    }

    private fun initTabBottom() {
        tabBottomLayout = activityProvider.findViewById<TabBottomLayout>(R.id.tab_bottom_layout).apply {
            alpha = 0.8f
        }
        val defaultColor = activityProvider.getResources().getColor(R.color.tabBottomDefaultColor)
        val tintColor = activityProvider.getResources().getColor(R.color.tabBottomTintColor)
        val homeInfo = TabBottomInfo(
            "首页",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_home),
            null,
            defaultColor,
            tintColor
        ).apply {
            fragment = HomePageFragment::class.java
        }
        val infoFavorite = TabBottomInfo(
            "收藏",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_favorite),
            null,
            defaultColor,
            tintColor
        ).apply {
            fragment = FavoriteFragment::class.java
        }

        /*val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.fire, null)

        val infoCategory = TabBottomInfo<String>(
            "分类",
            bitmap,
            bitmap
        )*/
        val infoCategory: TabBottomInfo<Int> = TabBottomInfo(
            "分类",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_category),
            null,
            defaultColor,
            tintColor
        ).apply {
            fragment = CategoryFragment::class.java
        }

        val infoRecommend = TabBottomInfo(
            "推荐",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_recommend),
            null,
            defaultColor,
            tintColor
        ).apply {
            fragment = RecommendFragment::class.java
        }

        val infoProfile = TabBottomInfo(
            "我的",
            "fonts/iconfont.ttf",
            activityProvider.getString(R.string.if_profile),
            null,
            defaultColor,
            tintColor
        ).apply {
            fragment = ProfileFragment::class.java
        }

        with(infoList) {
            add(homeInfo)
            add(infoRecommend)
            add(infoCategory)
            add(infoFavorite)
            add(infoProfile)
        }
        tabBottomLayout.inflateInfo(infoList)
        initFragmentTabView()
        tabBottomLayout.apply {
            addTabSelectedChangeListener(object : ITabLayout.OnTabSelectedListener<TabBottomInfo<*>> {
                override fun onTabSelectedChange(
                    index: Int,
                    prevInfo: TabBottomInfo<*>?,
                    nextInfo: TabBottomInfo<*>
                ) {
                    fragmentTabView.setCurrentItem(index)
                    this@MainActivityLogic.currentItemIndex = index
                }
            })
            defaultSelected(infoList[currentItemIndex])
        }

    }

    private fun initFragmentTabView() {
        val tabViewAdapter = TabViewAdapter(activityProvider.getSupportFragmentManager(), infoList)
        fragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view)
        fragmentTabView.adapter = tabViewAdapter
    }


    // 需要Activity提供的能力
    interface ActivityProvider {
        fun <T : View> findViewById(id: Int): T

        fun getResources(): Resources

        fun getSupportFragmentManager(): FragmentManager

        fun getString(@StringRes redId: Int): String
    }
}