package com.yangyang.library.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes


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

    fun newInstance(parent: ViewGroup, @LayoutRes resId: Int): View {
        return LayoutInflater.from(parent.context).inflate(resId, parent, false)
    }

    fun newInstance(context: Context, parent: ViewGroup?, @LayoutRes resId: Int): View {
        return LayoutInflater.from(context).inflate(resId, parent, false)
    }

    fun newInstance(context: Context, @LayoutRes resId: Int): View {
        return LayoutInflater.from(context).inflate(resId, null)
    }

}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.isVisible(): Boolean = this?.visibility == View.VISIBLE

fun View?.isGone(): Boolean = this?.visibility == View.GONE

fun View?.visibleOrGone(isVisible: Boolean, doIfVisible: ((Boolean) -> Unit)? = null) {
    this?.visibility = if (isVisible) View.VISIBLE else View.GONE
    if (this.isVisible()) {
        doIfVisible?.invoke(isVisible)
    }
}

fun View?.visibleOrInvisible(isVisible: Boolean) {
    this?.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
}

fun <T : View> Activity.lazyFind(@IdRes id: Int) = lazy<T>(LazyThreadSafetyMode.NONE) {
    findViewById(id)
}

//根据Id从ViewGroup中查找View, 用于inflate执行前
fun <T : View> ViewGroup.lazyFind(@IdRes id: Int) = lazy<T>(LazyThreadSafetyMode.NONE) {
    findViewById(id)
}

//根据Id从ViewGroup中查找View, 用于inflate执行前
fun <T : View> ViewGroup.lazyFindNullable(@IdRes id: Int) = lazy<T?>(LazyThreadSafetyMode.NONE) {
    findViewById(id)
}
