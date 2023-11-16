package com.yangyang.widget.banner

import android.content.Context
import android.view.animation.Interpolator
import android.widget.Scroller


/**
 * 用于设置滚动的时长
 *
 * Create by Yang Yang on 2023/11/16
 *
 * @param duration 值越大，滑动越慢
 */
class BannerScroller @JvmOverloads constructor(
    context: Context, interpolator: Interpolator?, private var duration: Int
) : Scroller(context, interpolator) {

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, duration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, this.duration)
    }
}