package com.yangyang.widget.banner

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.yangyang.widget.banner.bean.IBannerEntity
import com.yangyang.widget.banner.interfaces.IBanner
import com.yangyang.widget.banner.interfaces.IBindAdapter
import com.yangyang.widget.banner.interfaces.IIndicator


/**
 * Create by Yang Yang on 2023/11/15
 */
class BannerView @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr), IBanner {

    private val bannerViewDelegate = BannerViewDelegate(context, this)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BannerView).apply {
            val autoPlay: Boolean = getBoolean(R.styleable.BannerView_autoPlay, true)
            val loop: Boolean = getBoolean(R.styleable.BannerView_loop, false)
            val intervalTime: Int = getInteger(R.styleable.BannerView_intervalTime, -1)

            setAutoPlay(autoPlay)
            setLoop(loop)
            setIntervalTime(intervalTime.toLong())
            recycle()
        }
    }

    override fun setBannerData(layoutResId: Int, datas: List<IBannerEntity>) {
        bannerViewDelegate.setBannerData(layoutResId, datas)
    }

    override fun setBannerData(datas: List<IBannerEntity>) {
        bannerViewDelegate.setBannerData(datas)
    }

    override fun setIndicator(indicator: IIndicator<*>) {
        bannerViewDelegate.setIndicator(indicator)
    }

    override fun setAutoPlay(autoPlay: Boolean) {
        bannerViewDelegate.setAutoPlay(autoPlay)
    }

    override fun setLoop(loop: Boolean) {
        bannerViewDelegate.setLoop(loop)
    }

    override fun setIntervalTime(intervalTime: Long) {
        bannerViewDelegate.setIntervalTime(intervalTime)
    }

    override fun setBindAdapter(bindAdapter: IBindAdapter) {
        bannerViewDelegate.setBindAdapter(bindAdapter)
    }

    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        bannerViewDelegate.setOnPageChangeListener(onPageChangeListener)
    }

    override fun setOnBannerClickListener(onBannerClickListener: IBanner.OnBannerClickListener) {
        bannerViewDelegate.setOnBannerClickListener(onBannerClickListener)
    }

    override fun setScrollDuration(duration: Int) {
        bannerViewDelegate.setScrollDuration(duration)
    }


}