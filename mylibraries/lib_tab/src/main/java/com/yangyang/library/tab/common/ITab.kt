package com.yangyang.library.tab.common

import androidx.annotation.Px


/**
 * Create by Yang Yang on 2023/11/3
 */
interface ITab<D> : ITabLayout.OnTabSelectedListener<D> {

    fun setTabInfo(data: D)

    /**
     * 动态修改item大小
     *
     * @param height
     */
    fun resetHeight(@Px height: Int)
}