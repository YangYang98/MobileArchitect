package com.yangyang.widget.banner

import android.content.Context
import android.widget.FrameLayout.LayoutParams
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.yangyang.widget.banner.bean.IBannerEntity
import com.yangyang.widget.banner.indicator.SimpleIndicator
import com.yangyang.widget.banner.interfaces.IBanner
import com.yangyang.widget.banner.interfaces.IBindAdapter
import com.yangyang.widget.banner.interfaces.IIndicator


/**
 * Create by Yang Yang on 2023/11/15
 */
class BannerViewDelegate(private val context: Context, private val bannerView: BannerView) : IBanner, OnPageChangeListener {
    private var mBanners = mutableListOf<IBannerEntity>()
    private val mAdapter: BannerAdapter by lazy {
        BannerAdapter(context)
    }
    private var mIndicator: IIndicator<*>? = null
    private var mAutoPlay = true
        set(value) {
            field = value
            mViewPager.autoPlay = value
            mAdapter.autoPlay = value
        }
    private var mLoop = false
        set(value) {
            field = value
            mAdapter.loop = value
        }
    private var mOnPageChangeListener: OnPageChangeListener? = null
    private var mOnBannerClickListener: IBanner.OnBannerClickListener? = null
    private var mIntervalTime = 5000L
        set(value) {
            if (value >= 0) {
                field = value
                mViewPager.intervalTime = value
            }
        }

    private val mViewPager: AutoScrollViewPager by lazy {
        AutoScrollViewPager(context)
    }

    override fun setBannerData(layoutResId: Int, datas: List<IBannerEntity>) {
        mBanners.addAll(datas)
        init(layoutResId)
    }

    override fun setBannerData(datas: List<IBannerEntity>) {
        setBannerData(R.layout.layout_simple_banner_item, datas)
    }

    override fun setIndicator(indicator: IIndicator<*>) {
        this.mIndicator = indicator
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        mAutoPlay = autoPlay
    }

    override fun setLoop(loop: Boolean) {
        mLoop = loop
    }

    override fun setIntervalTime(intervalTime: Long) {
        mIntervalTime = intervalTime
    }

    override fun setBindAdapter(bindAdapter: IBindAdapter) {
        mAdapter.bindAdapter = bindAdapter
    }

    override fun setOnPageChangeListener(onPageChangeListener: OnPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener
    }

    override fun setOnBannerClickListener(onBannerClickListener: IBanner.OnBannerClickListener) {
        mOnBannerClickListener = onBannerClickListener
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (mAdapter.getRealCount() != 0) {
            mOnPageChangeListener?.onPageScrolled(position % mAdapter.getRealCount(), positionOffset, positionOffsetPixels)
        }
    }

    override fun onPageSelected(position: Int) {
        if (mAdapter.getRealCount() == 0) {
            return
        }

        val finalPosition = position % mAdapter.getRealCount()
        mOnPageChangeListener?.onPageSelected(finalPosition)
        mIndicator?.onPointChange(finalPosition, mAdapter.getRealCount())
    }

    override fun onPageScrollStateChanged(state: Int) {
        mOnPageChangeListener?.onPageScrollStateChanged(state)
    }

    private fun init(layoutResId: Int) {

        if (mIndicator == null) {
            mIndicator = SimpleIndicator(context)
        }

        mIndicator!!.onInflate(mBanners.size)
        with(mAdapter) {
            this.layoutResId = layoutResId
            setBannerData(mBanners)
            this.autoPlay = mAutoPlay
            loop = mLoop
            bannerClickListener = mOnBannerClickListener
        }

        with(mViewPager) {
            intervalTime = mIntervalTime
            addOnPageChangeListener(this@BannerViewDelegate)
            autoPlay = mAutoPlay
            adapter = mAdapter
        }

        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        if ((mLoop || mAutoPlay) && mAdapter.getRealCount() != 0) {
            val firstItem =  mAdapter.getFirstItem()
            mViewPager.setCurrentItem(firstItem, true)
        }

        with(bannerView) {
            removeAllViews()
            addView(mViewPager, lp)
            addView(mIndicator!!.get(), lp)
        }


    }
}