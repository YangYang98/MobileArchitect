package com.yangyang.widget.banner.interfaces

import com.yangyang.widget.banner.BannerAdapter
import com.yangyang.widget.banner.bean.IBannerEntity


/**
 * 数据绑定接口，基于该接口可以实现数据的绑定和框架层解耦
 *
 * Create by Yang Yang on 2023/11/15
 */
interface IBindAdapter {

    fun onBindAdapter(viewHolder: BannerAdapter.BannerViewHolder, bean: IBannerEntity, position: Int)
}