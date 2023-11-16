package com.yangyang.mobile.architect.demo

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.yangyang.library.log.L
import com.yangyang.mobile.architect.R
import com.yangyang.mobile.architect.demo.bean.BannerEntity
import com.yangyang.widget.banner.BannerAdapter
import com.yangyang.widget.banner.BannerView
import com.yangyang.widget.banner.bean.IBannerEntity
import com.yangyang.widget.banner.indicator.CircleIndicator
import com.yangyang.widget.banner.interfaces.IBanner
import com.yangyang.widget.banner.interfaces.IBindAdapter
import com.yangyang.widget.banner.interfaces.IIndicator


/**
 * Create by Yang Yang on 2023/11/15
 */
class BannerViewActivity : AppCompatActivity() {

    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )

    private var indicator: IIndicator<*>? = null
    private var autoPlay: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_banner_view)
        initView(CircleIndicator(this))
        findViewById<Switch>(R.id.auto_play).setOnCheckedChangeListener { _, isChecked ->
            autoPlay = isChecked
            initView(indicator)
        }
        findViewById<View>(R.id.tv_switch).setOnClickListener {

        }
    }

    private fun initView(indicator: IIndicator<*>?) {
        val mHiBanner = findViewById<BannerView>(R.id.banner)
        val moList: MutableList<IBannerEntity> = ArrayList()
        for (i in 0..5) {
            val bannerEntity = BannerEntity()
            bannerEntity.url = urls[i % urls.size]
            moList.add(bannerEntity)
        }
        if (indicator != null) {
            mHiBanner!!.setIndicator(indicator)
        }
        mHiBanner.setAutoPlay(autoPlay)
        mHiBanner.setIntervalTime(2000)
        mHiBanner.setScrollDuration(1000)
        //自定义布局
        mHiBanner.setBannerData(R.layout.banner_item_layout, moList)
        mHiBanner.setBindAdapter(object : IBindAdapter {
            override fun onBindAdapter(
                viewHolder: BannerAdapter.BannerViewHolder,
                bean: IBannerEntity,
                position: Int
            ) {
                val imageView: ImageView = viewHolder.findViewById(R.id.iv_image)
                Glide.with(this@BannerViewActivity).load(bean.url).into(imageView)
                val titleView: TextView = viewHolder.findViewById(R.id.tv_title)
                titleView.text = bean.url
                L.d("----position:", position.toString() + "url:" + bean.url)
            }
        })
        // 点击时触发
        mHiBanner.setOnBannerClickListener(object : IBanner.OnBannerClickListener {
            override fun onBannerClick(
                viewHolder: BannerAdapter.BannerViewHolder,
                bean: IBannerEntity,
                position: Int
            ) {
                L.d("tag", "onClick$position");
            }
        })
    }
}