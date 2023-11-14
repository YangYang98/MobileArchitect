package com.yangyang.widget.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import com.yangyang.library.log.L
import com.yangyang.library.utils.ScrollUtil
import com.yangyang.widget.refresh.header.AbsHeaderView
import com.yangyang.widget.refresh.header.AbsHeaderView.RefreshState
import com.yangyang.widget.refresh.header.SimpleHeaderView
import com.yangyang.widget.refresh.interfaces.IRefresh
import kotlin.math.abs


/**
 * Create by Yang Yang on 2023/11/13
 */
class RefreshLayout @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr), IRefresh {

    private var mRefreshListener: IRefresh.RefreshListener? = null
    private val mGestureDetector: GestureDetector by lazy {
        GestureDetector(context, gestureDetector)
    }
    private var mHeaderView: AbsHeaderView = SimpleHeaderView(context)
    //刷新时是否禁止滚动
    private var disableRefreshScroll: Boolean = false
    private var mState: RefreshState = RefreshState.STATE_INIT

    private var mLastY = 0

    private val mAutoScroller: AutoScroller by lazy {
        AutoScroller()
    }

    companion object {
        private val TAG = RefreshLayout::class.java.simpleName
    }

    override fun setDisableRefreshScroll(disableRefreshScroll: Boolean) {
        this.disableRefreshScroll = disableRefreshScroll
    }

    override fun refreshFinished() {
        val head = getChildAt(0)
        mHeaderView.apply {
            onFinish()
            mState = RefreshState.STATE_INIT
        }
        val bottom = head.bottom
        if (bottom > 0) {
            recover(bottom)
        }
        mState = RefreshState.STATE_INIT
    }

    override fun setRefreshListener(refreshListener: IRefresh.RefreshListener?) {
        this.mRefreshListener = refreshListener
    }

    override fun setRefreshHeaderView(absHeaderView: AbsHeaderView) {
        removeView(mHeaderView)
        this.mHeaderView = absHeaderView
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(mHeaderView, 0, lp)
    }

    private val gestureDetector = object : SimpleOnGestureListener() {
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (abs(distanceX) > abs(distanceY) || mRefreshListener?.enableRefresh() == false) {
                L.i(TAG, "横向滑动，或刷新被禁止则不处理")
                return false
            }

            if (disableRefreshScroll && mState == RefreshState.STATE_REFRESH) {
                L.i(TAG, "刷新时禁止滑动")
                return true
            }

            val head = getChildAt(0)
            val child = ScrollUtil.findScrollableChildFromSecond(this@RefreshLayout)
            if (ScrollUtil.childScrolled(child)) {
                L.i(TAG, "列表发生了滚动 不处理")
                return false
            }

            //没有刷新或没有达到可以刷新的距离，且头部已经划出或下拉
            if (
                (mState != RefreshState.STATE_REFRESH || head.bottom <= mHeaderView.mPullRefreshHeight) &&
                (head.bottom > 0 || distanceY <= 0f)
                ) {
                if (mState != RefreshState.STATE_OVER_RELEASE) {
                    val moveDis = if (child.top < mHeaderView.mPullRefreshHeight) {
                        mLastY / mHeaderView.minDamp
                    } else {
                        mLastY / mHeaderView.maxDamp
                    }
                    val bool = moveView(moveDis.toInt(), true)
                    L.e(TAG, "head:top  ${head.top}  bottom:  ${head.bottom}")
                    mLastY = (-distanceY).toInt()
                    return bool
                } else {
                    return false
                }
            } else {
                return false
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        val head = getChildAt(0)
        val child = getChildAt(1)
        when (ev.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_POINTER_INDEX_MASK -> {
                //松开手
                if (head.bottom > 0 && mState != RefreshState.STATE_REFRESH) {
                    recover(child.top)
                    return false
                }
                mLastY = 0
            }
        }

        val consumed = mGestureDetector.onTouchEvent(ev)
        if ((consumed || (mState != RefreshState.STATE_INIT && mState != RefreshState.STATE_REFRESH)) && head.bottom != 0) {
            ev.action = MotionEvent.ACTION_CANCEL
            return super.dispatchTouchEvent(ev)
        }

        return if (consumed) {
            true
        } else {
            super.dispatchTouchEvent(ev)
        }
    }

    private fun recover(dis: Int) {
        if (dis > mHeaderView.mPullRefreshHeight) {
            mAutoScroller.recover(dis - mHeaderView.mPullRefreshHeight)
            mState = RefreshState.STATE_OVER_RELEASE
            L.i(TAG, "超过最小刷新距离后，恢复到刷新位置")
        } else {
            mAutoScroller.recover(dis)
            L.i(TAG, "没达到最小刷新距离，恢复到原始")
        }
    }

    /**
     * 根据偏移量移动header与child
     *
     * @param offsetY 偏移量 需要移动的Y轴距离
     * @param nonAuto true: 手动触发滚动  false：自动触发滚动
     * @return
     */
    private fun moveView(offsetY: Int, nonAuto: Boolean): Boolean {
        var realOffsetY = offsetY
        val head =  getChildAt(0)
        val child = getChildAt(1)
        val childTop = child.top + realOffsetY
        if (childTop <= 0) {
            realOffsetY = -child.top
            //移动head与child的位置，到原始位置
            head.offsetTopAndBottom(realOffsetY)
            child.offsetTopAndBottom(realOffsetY)
            if (mState != RefreshState.STATE_REFRESH) {
                mState = RefreshState.STATE_INIT
            }
        } else if (mState == RefreshState.STATE_REFRESH && childTop > mHeaderView.mPullRefreshHeight) {
            //如果正在刷新中，禁止继续下拉
            return false
        } else if (childTop <= mHeaderView.mPullRefreshHeight) {
            //下拉高度 还没超出设定的刷新距离
            if (mHeaderView.mState != RefreshState.STATE_VISIBLE && nonAuto) {
                //头部开始显示
                mHeaderView.onHeaderVisible()
                mHeaderView.mState = RefreshState.STATE_VISIBLE
                mState = RefreshState.STATE_VISIBLE
            }
            head.offsetTopAndBottom(realOffsetY)
            child.offsetTopAndBottom(realOffsetY)
            if (childTop == mHeaderView.mPullRefreshHeight && mState == RefreshState.STATE_OVER_RELEASE) {
                refresh()
            }
        } else {
            //超出最小刷新位置
            if (mHeaderView.mState != RefreshState.STATE_OVER && nonAuto) {
                mHeaderView.apply {
                    onOver()
                    mState = RefreshState.STATE_OVER
                }
            }
            head.offsetTopAndBottom(realOffsetY)
            child.offsetTopAndBottom(realOffsetY)
        }

        mHeaderView.onScroll(head.bottom, mHeaderView.mPullRefreshHeight)

        return true
    }

    private fun refresh() {
        mRefreshListener?.let {
            mState = RefreshState.STATE_REFRESH
            mHeaderView.apply {
                onRefresh()
                mState = RefreshState.STATE_REFRESH
            }
            it.onRefresh()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val head = getChildAt(0)
        val child = getChildAt(1)
        if (head != null && child != null) {
            val childTop = child.top
            if (mState == RefreshState.STATE_REFRESH) {
                head.layout(left, mHeaderView.mPullRefreshHeight - head.measuredHeight, right, mHeaderView.mPullRefreshHeight)
                child.layout(left, mHeaderView.mPullRefreshHeight, right, mHeaderView.mPullRefreshHeight + child.measuredHeight)
            } else {
                head.layout(left, childTop - head.measuredHeight, right, childTop)
                child.layout(left, childTop, right, childTop + child.measuredHeight)
            }
        }

        var otherChild: View
        //让RefreshLayout节点下两个以上的child能够不跟随手势移动以实现一些特殊效果，如悬浮的效果
        for (i in 2 until childCount) {
            otherChild = getChildAt(i)
            otherChild.layout(0, top, right, bottom)
        }
    }

    inner class AutoScroller : Runnable {

        private val mScroller: Scroller by lazy {
            Scroller(context, LinearInterpolator())
        }
        private var mIsFinished = true
        private var mLastLocationY = 0

        override fun run() {
            if (mScroller.computeScrollOffset()) {
                //还未滚动完成
                L.i(TAG, "Scroller计算的偏移量${mScroller.currY}")
                L.i(TAG, "自动滚动的偏移量${mLastLocationY - mScroller.currY}")
                
                moveView(mLastLocationY - mScroller.currY, false)
                mLastLocationY = mScroller.currY
                L.i(TAG, "mLastY${mLastLocationY}")
                post(this)
            } else {
                removeCallbacks(this)
                mIsFinished = true
            }
        }

        fun recover(distance: Int) {
            if (distance <= 0) {
                return
            }
            L.i(TAG, "要恢复到的位置$distance")

            removeCallbacks(this)

            mLastLocationY = 0
            mIsFinished = false
            mScroller.startScroll(0, 0, 0, distance, 300)
            post(this)
        }

    }
}