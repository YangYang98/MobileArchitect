package com.yangyang.library.tab.common

import android.view.ViewGroup


/**
 * Create by Yang Yang on 2023/11/3
 */
interface ITabLayout<Tab : ViewGroup, D> {

    fun findTab(data: D): Tab?

    fun addTabSelectedChangeListener(listener: OnTabSelectedListener<D>)

    fun defaultSelected(defaultInfo: D)

    fun inflateInfo(infoList: List<D>)

    interface OnTabSelectedListener<D> {
        /**
         * @param index 选中的Tab的下标
         * @param prevInfo 原来选中的是哪个Tab
         * @param nextInfo 现在选中的是哪个Tab
         */
        fun onTabSelectedChange(index: Int, prevInfo: D?, nextInfo: D)
    }
}