package com.yangyang.library.tab.top

 import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.yangyang.library.tab.common.ITabLayout


/**
 * Create by Yang Yang on 2023/11/8
 */
class TabTopLayout @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : HorizontalScrollView(context, attrs, defStyleAttr), ITabLayout<TabTop, TabTopInfo<*>> {
    private val tabSelectedChangeListeners = mutableListOf<ITabLayout.OnTabSelectedListener<TabTopInfo<*>>>()
    private var selectedInfo: TabTopInfo<*>? = null
    private var infoList: List<TabTopInfo<*>> = emptyList()

    init {
        isVerticalScrollBarEnabled = false
    }

    override fun findTab(data: TabTopInfo<*>): TabTop? {
        val tabsContainer = getRootLinearLayout(false)
        for (i in 0 until tabsContainer.childCount) {
            val child = tabsContainer.getChildAt(i)
            if (child is TabTop) {
                if (child.tabInfo == data) {
                    return child
                }
            }
        }
        return null
    }

    override fun addTabSelectedChangeListener(listener: ITabLayout.OnTabSelectedListener<TabTopInfo<*>>) {
        tabSelectedChangeListeners.add(listener)
    }

    override fun defaultSelected(defaultInfo: TabTopInfo<*>) {
        onSelected(defaultInfo)
    }

    private fun onSelected(nextInfo: TabTopInfo<*>) {
        tabSelectedChangeListeners.forEach {
            it.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo)
        }
        selectedInfo = nextInfo
    }

    override fun inflateInfo(infoList: List<TabTopInfo<*>>) {
        if (infoList.isEmpty()) {
            return
        }

        this.infoList = infoList
        selectedInfo = null

        //清除之前添加的TabTop listener，Tips：foreach remove问题
        tabSelectedChangeListeners.iterator().apply {
            while (hasNext()) {
                if (next() is TabTop) {
                    remove()
                }
            }
        }

        val tabsContainer = getRootLinearLayout(true)

        infoList.forEach { info ->

            val tabTop = TabTop(context)
            tabSelectedChangeListeners.add(tabTop)
            tabTop.setTabInfo(info)
            tabsContainer.addView(tabTop)
            tabTop.setOnClickListener {
                onSelected(info)
            }
        }
    }

    private fun getRootLinearLayout(needClearChild: Boolean): LinearLayout {
        var rootView: LinearLayout? = getChildAt(0) as? LinearLayout
        if (rootView == null) {
            rootView = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
            }
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            addView(rootView, lp)
        } else if (needClearChild) {
            rootView.removeAllViews()
        }
        return rootView
    }
}