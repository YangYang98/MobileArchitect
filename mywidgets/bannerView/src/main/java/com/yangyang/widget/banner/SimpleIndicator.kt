package com.yangyang.widget.banner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.yangyang.widget.banner.interfaces.IIndicator


/**
 * Create by Yang Yang on 2023/11/15
 */
class SimpleIndicator @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr), IIndicator<FrameLayout> {

    override fun get(): FrameLayout {
        return FrameLayout(context, attrs)
    }

    override fun onInflate(count: Int) {
        
    }

    override fun onPointChange(current: Int, count: Int) {
        
    }
}