package com.yangyang.widget.banner

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


/**
 * Create by Yang Yang on 2023/11/14
 */
class AutoScrollViewPager @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewPager(context, attrs) {

    var autoPlay = true
        set(value) {
            field = value
            if (!value) {
                mHandler.removeCallbacks(mRunnable)
            }
        }
    private var isLayout = false
    private var mHandler = Handler(Looper.getMainLooper())
    private var mRunnable = object : Runnable {
        override fun run() {
            next()
            mHandler.postDelayed(this, intervalTime)
        }
    }
    var intervalTime: Long = 5000

    fun start() {
        mHandler.removeCallbacksAndMessages(null)
        if (autoPlay) {
            mHandler.postDelayed(mRunnable, intervalTime)
        }
    }

    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }

    fun setScrollDuration(duration: Int) {
        try {
            val scrollerField = ViewPager::class.java.getDeclaredField("mScroller")
            scrollerField.isAccessible = true
            scrollerField.set(this, BannerScroller(context, null, duration))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun next(): Int {
        var nextPosition = -1
        if (adapter == null || adapter!!.count <= 1) {
            stop()
            return nextPosition
        }
        nextPosition = currentItem + 1
        if (nextPosition >= adapter!!.count) {
            nextPosition = (adapter as BannerAdapter).getFirstItem()
        }

        setCurrentItem(nextPosition, true)
        return nextPosition
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                start()
            }
            else -> {
                stop()
            }
        }

        return super.onTouchEvent(ev)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (isLayout && adapter != null && adapter!!.count > 0) {
            try {
                //fix 使用RecyclerView + ViewPager bug https://blog.csdn.net/u011002668/article/details/72884893
                val mScroller = ViewPager::class.java.getDeclaredField("mFirstLayout").apply {
                    isAccessible = true
                    set(this, false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        start()
    }

    override fun onDetachedFromWindow() {
        //fix 使用RecyclerView + ViewPager bug
        if (context is Activity) {
            if (context.isFinishing) {
                super.onDetachedFromWindow()
            }
        } else {
            super.onDetachedFromWindow()
        }

        stop()
    }

}