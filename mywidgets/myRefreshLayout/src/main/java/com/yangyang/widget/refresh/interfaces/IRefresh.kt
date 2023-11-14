package com.yangyang.widget.refresh.interfaces

import com.yangyang.widget.refresh.header.AbsHeaderView


/**
 * Create by Yang Yang on 2023/11/13
 */
interface IRefresh {

    /**
     * 刷新时是否禁止滚动
     *
     * @param disableRefreshScroll 是否禁止滚动
     */
    fun setDisableRefreshScroll(disableRefreshScroll: Boolean)

    /**
     * 刷新完成
     */
    fun refreshFinished()

    fun setRefreshListener(refreshListener: RefreshListener?)

    /**
     * 设置下拉刷新的视图
     *
     * @param absHeaderView 下拉刷新的视图
     */
    fun setRefreshHeaderView(absHeaderView: AbsHeaderView)

    interface RefreshListener {
        fun onRefresh()
        fun enableRefresh(): Boolean
    }

}