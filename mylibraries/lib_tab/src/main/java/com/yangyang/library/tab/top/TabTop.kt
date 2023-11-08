package com.yangyang.library.tab.top

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.yangyang.library.tab.R
import com.yangyang.library.tab.common.ITab
import com.yangyang.library.utils.ColorUtil
import com.yangyang.library.utils.gone
import com.yangyang.library.utils.visible
import com.yangyang.library.utils.visibleOrGone


/**
 * Create by Yang Yang on 2023/11/8
 */
class TabTop @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): RelativeLayout(context, attrs, defStyleAttr), ITab<TabTopInfo<*>> {

    var tabInfo: TabTopInfo<*>? = null
        private set

    var tabImageView: ImageView
        private set
    var tabNameView: TextView
        private set
    private var indicator: View

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_tab_top, this)
        tabImageView = findViewById(R.id.iv_image)
        tabNameView = findViewById(R.id.tv_name)
        indicator = findViewById(R.id.tab_top_indicator)
    }

    override fun setTabInfo(data: TabTopInfo<*>) {
        tabInfo = data
        inflateInfo(selected = false, isInit = true)
    }

    private fun inflateInfo(selected: Boolean, isInit: Boolean) {
        tabInfo?.let {tabInfo ->
            if (tabInfo.tabType == TabTopInfo.TabType.TEXT) {
                if (isInit) {
                    tabNameView.visible()
                    tabImageView.gone()
                    if (tabInfo.name.isNotEmpty()) {
                        tabNameView.text = tabInfo.name
                    }
                }

                indicator.visibleOrGone(selected)
                tabNameView.setTextColor(
                    if (selected) {
                        ColorUtil.getTextColor(tabInfo.tintColor ?: Color.BLACK)
                    } else {
                        ColorUtil.getTextColor(tabInfo.defaultColor ?: Color.BLACK)
                    }
                )

            } else if (tabInfo.tabType == TabTopInfo.TabType.TEXT) {
                if (isInit) {
                    tabNameView.gone()
                    tabImageView.visible()
                }

                indicator.visibleOrGone(selected)
                tabImageView.setImageBitmap(
                    if (selected) {
                        tabInfo.selectedBitmap
                    } else {
                        tabInfo.defaultBitmap
                    }
                )
            }
        }
    }

    override fun resetHeight(height: Int) {
        val lp = layoutParams.apply {
            this.height = height
        }
        layoutParams = lp
        tabNameView.gone()
    }

    override fun onTabSelectedChange(
        index: Int,
        prevInfo: TabTopInfo<*>?,
        nextInfo: TabTopInfo<*>
    ) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return
        }
        inflateInfo(prevInfo != tabInfo, false)
    }
}