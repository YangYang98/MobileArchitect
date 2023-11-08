package com.yangyang.common.ui.tab

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * Create by Yang Yang on 2023/11/8
 */
class FragmentTabView @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var adapter: TabViewAdapter? = null
        set(value) {
            if (field != null || value == null) {
                return
            }
            field = value
            currentPosition = -1
        }
    var currentPosition: Int = -1
        private set

    fun setCurrentItem(position: Int) {
        checkAdapter()
        if (position < 0 || position >= adapter!!.getCount()) {
            return
        }
        if (currentPosition != position) {
            currentPosition = position
            adapter!!.instantiateItem(this, position)
        }
    }

    fun checkAdapter() {
        if (adapter == null) {
            throw IllegalArgumentException("adapter is null")
        }
    }
}