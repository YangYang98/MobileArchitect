package com.yangyang.widget.banner.indicator

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
        return this
    }

    override fun onInflate(count: Int) {
        
    }

    override fun onPointChange(current: Int, count: Int) {
        
    }
}