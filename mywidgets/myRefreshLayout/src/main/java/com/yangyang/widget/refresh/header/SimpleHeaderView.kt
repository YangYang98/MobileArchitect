package com.yangyang.widget.refresh.header

import android.content.Context
import android.util.AttributeSet


/**
 * Create by Yang Yang on 2023/11/13
 */
class SimpleHeaderView @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AbsHeaderView(context, attrs, defStyleAttr) {

    override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {
        
    }

    override fun onHeaderVisible() {
        
    }

    override fun onOver() {
        
    }

    override fun onRefresh() {
        
    }

    override fun onFinish() {
        
    }
}