package com.yangyang.library.tab.bottom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import com.yangyang.library.tab.R
import com.yangyang.library.tab.common.ITab


/**
 * Create by Yang Yang on 2023/11/3
 */
class TabBottom @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): RelativeLayout(context, attrs, defStyleAttr), ITab<TabBottomInfo<*>>{

    var tabInfo: TabBottomInfo<*>? = null
        private set

    var tabImageView: ImageView
        private set
    var tabIconView: TextView
        private set
    var tabNameView: TextView
        private set

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_tab_bottom, this)
        tabImageView = findViewById(R.id.iv_image)
        tabIconView = findViewById(R.id.tv_icon)
        tabNameView = findViewById(R.id.tv_name)
    }

    override fun setTabInfo(data: TabBottomInfo<*>) {
        tabInfo = data

        inflateInfo(false, true)
    }

    private fun inflateInfo(selected: Boolean, isInit: Boolean) {
        tabInfo?.let { tabInfo ->
            if (tabInfo.tabType == TabBottomInfo.TabType.ICON) {
                if (isInit) {
                    tabImageView.visibility = View.GONE
                    tabIconView.visibility = View.VISIBLE
                    val typeface = Typeface.createFromAsset(context.assets, tabInfo.iconFont)
                    tabIconView.typeface = typeface
                    if (tabInfo.name.isNotEmpty()) {
                        tabNameView.text = tabInfo.name
                    }
                }

                if (selected) {
                    with(tabIconView) {
                        text = if (tabInfo.selectedIconName.isNullOrEmpty()) tabInfo.defaultIconName else tabInfo.selectedIconName
                        setTextColor(getTextColor(tabInfo.tintColor ?: Color.BLACK))
                    }
                    tabNameView.setTextColor(getTextColor(tabInfo.tintColor ?: Color.BLACK))
                } else {
                    with(tabIconView) {
                        text = tabInfo.defaultIconName
                        setTextColor(getTextColor(tabInfo.defaultColor ?: Color.BLACK))
                    }
                    tabNameView.setTextColor(getTextColor(tabInfo.defaultColor ?: Color.BLACK))
                }
            } else if (tabInfo.tabType == TabBottomInfo.TabType.BITMAP) {
                if (isInit) {
                    tabImageView.visibility = View.VISIBLE
                    tabImageView.visibility = View.GONE
                    if (tabInfo.name.isNotEmpty()) {
                        tabNameView.text = tabInfo.name
                    }
                }

                if (selected) {
                    tabImageView.setImageBitmap(tabInfo.selectedBitmap)
                } else {
                    tabImageView.setImageBitmap(tabInfo.defaultBitmap)
                }
            }
        }

    }

    override fun resetHeight(height: Int) {
        val layoutParams = layoutParams.apply {
            this.height = height
        }
        setLayoutParams(layoutParams)
        tabNameView.visibility = View.GONE
    }

    override fun onTabSelectedChange(
        index: Int,
        prevInfo: TabBottomInfo<*>?,
        nextInfo: TabBottomInfo<*>
    ) {
        if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
            return
        }
        inflateInfo(prevInfo != tabInfo, false)
    }

    @ColorInt
    private fun getTextColor(color: Any): Int {
        return if (color is String) {
            Color.parseColor(color)
        } else {
            color as Int
        }
    }
}

