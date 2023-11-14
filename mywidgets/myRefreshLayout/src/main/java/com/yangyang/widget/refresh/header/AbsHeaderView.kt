package com.yangyang.widget.refresh.header

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.yangyang.library.utils.DensityUtil


/**
 * Create by Yang Yang on 2023/11/13
 */
abstract class AbsHeaderView @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var mState = RefreshState.STATE_INIT
    /**
     * 触发下拉刷新 需要的最小高度
     */
    var mPullRefreshHeight: Int = DensityUtil.dp2px(66f)

    /**
     * 最小阻尼
     */
    var minDamp = 1.6f

    /**
     * 最大阻尼 越大越难以滑动
     */
    var maxDamp = 2.2f

    abstract fun onScroll(scrollY: Int, pullRefreshHeight: Int)

    abstract fun onHeaderVisible()

    /**
     * 超过Overlay，释放就会加载
     */
    abstract fun onOver()

    /**
     * 开始刷新
     */
    abstract fun onRefresh()

    /**
     * 刷新完成
     */
    abstract fun onFinish()

    enum class RefreshState {
        /**
         * 初始态
         */
        STATE_INIT,

        /**
         * Header展示的状态
         */
        STATE_VISIBLE,

        /**
         * 超出可刷新距离的状态
         */
        STATE_OVER,

        /**
         * 刷新中的状态
         */
        STATE_REFRESH,

        /**
         * 超出刷新位置松开手后的状态
         */
        STATE_OVER_RELEASE
    }

}