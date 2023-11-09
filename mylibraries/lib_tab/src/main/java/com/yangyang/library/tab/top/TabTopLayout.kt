package com.yangyang.library.tab.top

 import android.content.Context
 import android.graphics.Rect
 import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.yangyang.library.tab.common.ITabLayout
 import com.yangyang.library.utils.DensityUtil
 import kotlin.math.abs


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

        autoScroll(nextInfo)
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

    private var tabWidth = 0
    private fun autoScroll(nextInfo: TabTopInfo<*>) {
        val clickTabTop = findTab(nextInfo) ?: return

        val index = infoList.indexOf(nextInfo)
        val loc = IntArray(2)

        clickTabTop.getLocationInWindow(loc)
        tabWidth = clickTabTop.width

        val scrollWidth =
        if ((loc[0] + tabWidth / 2) > DensityUtil.getDisplayWidthInPx(context) / 2) {
            //点击了屏幕右侧
            rangeScrollWidth(index, 2)
        } else {
            //点击了屏幕左侧
            rangeScrollWidth(index, -2)
        }

        smoothScrollTo(scrollX + scrollWidth, 0)
    }

    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前向后的范围
     * @return 可滚动的范围
     */
    private fun rangeScrollWidth(index: Int, range: Int): Int {
        var scrollWidth = 0
        for (i in 0 .. abs(range)) {
            val next = if (range < 0) {
                index + range + i
            } else {
                index + range - i
            }

            if (next >= 0 && next < infoList.size) {
                if (range < 0) {
                    scrollWidth -= getScrollWidth(next, false)
                } else {
                    scrollWidth += getScrollWidth(next, true)
                }
            }
        }

        return scrollWidth
    }

    /**
     * 指定位置的控件可滚动的距离
     *
     * @param index   指定位置的控件
     * @param toRight 是否是点击了屏幕右侧
     * @return 可滚动的距离
     */
    private fun getScrollWidth(index: Int, toRight: Boolean): Int {
        val target = findTab(infoList[index]) ?: return 0

        val rect = Rect()
        target.getLocalVisibleRect(rect)

        if (toRight) {
            return if (rect.right > tabWidth) {
                //right坐标大于控件的宽度时，说明完全没有显示
                tabWidth
            } else {
                //显示部分，减去已显示的宽度
                tabWidth - rect.right
            }
        } else {
            if (rect.left <= -tabWidth) {
                //left坐标小于等于-控件的宽度，说明完全没有显示
                return tabWidth
            } else if (rect.left > 0) {
                //显示部分
                return rect.left
            }
        }
        return 0
    }
}