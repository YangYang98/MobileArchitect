package com.yangyang.library.tab.bottom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.yangyang.library.tab.R
import com.yangyang.library.tab.common.ITabLayout
import com.yangyang.library.tab.common.ITabLayout.OnTabSelectedListener
import com.yangyang.library.utils.DensityUtil
import com.yangyang.library.utils.ViewUtil


/**
 * Create by Yang Yang on 2023/11/7
 */
class TabBottomLayout @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ITabLayout<TabBottom, TabBottomInfo<*>> {

    private val tabSelectedChangeListeners = mutableListOf<OnTabSelectedListener<TabBottomInfo<*>>>()
    private var selectedInfo: TabBottomInfo<*>? = null
    var bottomAlpha = 1f
    //TabBottom高度
    private var tabBottomHeight = 50f
    //TabBottom的头部线条高度
    private val bottomLineHeight = 0.5f
    //TabBottom的头部线条颜色
    private val bottomLineColor = "#dfe0e1"
    private var infoList: List<TabBottomInfo<*>> = emptyList()

    companion object {
        private const val TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM"

    }

    override fun findTab(data: TabBottomInfo<*>): TabBottom? {
        val fl: FrameLayout = findViewWithTag(TAG_TAB_BOTTOM)
        for (i in 0 until fl.childCount) {
            val child = fl.getChildAt(i)
            if (child is TabBottom) {
                if (child.tabInfo == data) {
                    return child
                }
            }
        }
        return null
    }

    override fun addTabSelectedChangeListener(listener: OnTabSelectedListener<TabBottomInfo<*>>) {
        tabSelectedChangeListeners.add(listener)
    }

    override fun defaultSelected(defaultInfo: TabBottomInfo<*>) {
        onSelected(defaultInfo)
    }

    override fun inflateInfo(infoList: List<TabBottomInfo<*>>) {
        if (infoList.isEmpty()) {
            return
        }

        this.infoList = infoList

        for (i in childCount - 1 downTo 1) {
            removeViewAt(i)
        }
        selectedInfo = null
        addBackground()

        //清除之前添加的TabBottom listener，Tips：foreach remove问题
        tabSelectedChangeListeners.iterator().apply {
            while (hasNext()) {
                if (next() is TabBottom) {
                    remove()
                }
            }
        }

        val tabItemHeight = DensityUtil.dp2px(tabBottomHeight)
        //为何不用LinearLayout:当动态改变child大小后Gravity.BOTTOM会失效
        val tabsContainer = FrameLayout(context)
        // 屏幕宽度 / BottomInfo的个数 就是每个TabBottom的宽度
        val tabItemWidth = DensityUtil.getDisplayWidthInPx(context) / infoList.size
        tabsContainer.tag = TAG_TAB_BOTTOM

        infoList.forEachIndexed { index, tabBottomInfo ->
            val params = LayoutParams(tabItemWidth, tabItemHeight).apply {
                gravity = Gravity.BOTTOM
                leftMargin = index * width
            }
            val tabBottom = TabBottom(context)
            tabSelectedChangeListeners.add(tabBottom)
            tabBottom.setTabInfo(tabBottomInfo)
            tabsContainer.addView(tabBottom, params)
            tabBottom.setOnClickListener {
                onSelected(tabBottomInfo)
            }
        }

        val flParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.BOTTOM
        }
        addView(tabsContainer, flParams)

        addBottomLine()

        fixContentView()
    }

    private fun onSelected(nextInfo: TabBottomInfo<*>) {
        tabSelectedChangeListeners.forEach {
            it.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo)
        }
        selectedInfo = nextInfo
    }

    private fun addBackground() {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_tab_bg, null)
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dp2px(tabBottomHeight)).apply {
            gravity = Gravity.BOTTOM
        }
        addView(view, lp)
        view.alpha = bottomAlpha
    }

    private fun addBottomLine() {
        val bottomLine = View(context).apply {
            setBackgroundColor(Color.parseColor(bottomLineColor))
        }
        val bottomLineParams = LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dp2px(bottomLineHeight)).apply {
            gravity = Gravity.BOTTOM
            bottomMargin = DensityUtil.dp2px(tabBottomHeight)
        }
        addView(bottomLine, bottomLineParams)
        bottomLine.alpha = bottomAlpha
    }

    private fun fixContentView() {
        if (getChildAt(0) !is ViewGroup) {
            return
        }

        val rootView = getChildAt(0) as? ViewGroup
        var targetView: ViewGroup? = ViewUtil.findTypeView(rootView, RecyclerView::class.java)
        if (targetView == null) {
            targetView = ViewUtil.findTypeView(rootView, ScrollView::class.java)
        }
        if (targetView == null) {
            targetView = ViewUtil.findTypeView(rootView, AbsListView::class.java)
        }
        targetView?.setPadding(0, 0, 0, DensityUtil.dp2px(tabBottomHeight))
        targetView?.clipToPadding = false
    }
}