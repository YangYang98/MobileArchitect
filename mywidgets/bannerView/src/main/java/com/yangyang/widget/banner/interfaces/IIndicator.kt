package com.yangyang.widget.banner.interfaces

import android.view.View

/**
 * Create by Yang Yang on 2023/11/15
 */

interface IIndicator<T : View> {
    fun get(): T

    /**
     * 初始化Indicator
     *
     * @param count 幻灯片数量
     */
    fun onInflate(count: Int)

    /**
     * 切换回调
     *
     * @param current 切换到的位置
     * @param count   数量
     */
    fun onPointChange(current: Int, count: Int)
}