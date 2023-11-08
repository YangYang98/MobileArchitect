package com.yangyang.library.utils

import android.view.View
import android.view.ViewGroup


/**
 * Create by Yang Yang on 2023/11/8
 */
object ViewUtil {

    /**
     * 获取指定类型的子View
     *
     * @param group viewGroup
     * @param cls   如：RecyclerView.class
     * @param <T>
     * @return 指定类型的View
    </T> */
    fun <T> findTypeView(group: ViewGroup?, cls: Class<T>): T? {
        if (group == null) {
            return null
        }
        val deque: ArrayDeque<View> = ArrayDeque()
        deque.add(group)
        while (!deque.isEmpty()) {
            val node = deque.removeFirst()
            if (cls.isInstance(node)) {
                return cls.cast(node)
            } else if (node is ViewGroup) {
                var i = 0
                val count = node.childCount
                while (i < count) {
                    deque.add(node.getChildAt(i))
                    i++
                }
            }
        }
        return null
    }

}