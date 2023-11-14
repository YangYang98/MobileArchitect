package com.yangyang.library.utils

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView


/**
 * Create by Yang Yang on 2023/11/13
 */
object ScrollUtil {

    /**
     * 从第二个child开始查找可以滚动的child
     *
     * @param viewGroup
     * @return 可以滚动的child
     */
    fun findScrollableChildFromSecond(viewGroup: ViewGroup): View {
        val child = viewGroup.getChildAt(1)
        if (child is RecyclerView || child is AdapterView<*>) {
            return child
        }
        if (child is ViewGroup) {
            val tempChild = child.getChildAt(0)
            if (tempChild is RecyclerView || tempChild is AdapterView<*>) {
                return tempChild
            }
        }

        return child ?: viewGroup

    }

    fun childScrolled(child: View): Boolean {
        if (child is AdapterView<*>) {
            if (child.firstVisiblePosition != 0 ||
                (child.firstVisiblePosition == 0 && child.getChildAt(0) != null && child.getChildAt(0).top < 0)
                ) {
                return true
            }
        } else if (child.scrollY > 0) {
            return true
        }

        if (child is RecyclerView) {
            val view = child.getChildAt(0)
            val firstPosition = child.getChildAdapterPosition(view)
            return firstPosition != 0 || view.top != 0
        }

        return false
    }
}