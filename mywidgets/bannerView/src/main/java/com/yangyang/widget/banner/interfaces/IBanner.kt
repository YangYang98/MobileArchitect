package com.yangyang.widget.banner.interfaces

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.yangyang.widget.banner.BannerAdapter
import com.yangyang.widget.banner.bean.IBannerEntity


/**
 * Create by Yang Yang on 2023/11/15
 */
interface IBanner {

    fun setBannerData(@LayoutRes layoutResId: Int, datas: List<IBannerEntity>)

    fun setBannerData(datas: List<IBannerEntity>)

    fun setIndicator(indicator: IIndicator<*>)

    fun setAutoPlay(autoPlay: Boolean)

    fun setLoop(loop: Boolean)

    fun setIntervalTime(intervalTime: Long)

    fun setBindAdapter(bindAdapter: IBindAdapter)

    fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener)

    fun setOnBannerClickListener(onBannerClickListener: OnBannerClickListener)

    fun setScrollDuration(duration: Int)

    interface OnBannerClickListener {

        fun onBannerClick(viewHolder: BannerAdapter.BannerViewHolder, bean: IBannerEntity, position: Int)
    }
}