package com.yangyang.widget.refresh.header

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.yangyang.widget.refresh.R


/**
 * Create by Yang Yang on 2023/11/13
 */
class TextHeaderView @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AbsHeaderView(context, attrs, defStyleAttr) {

    private lateinit var mText: TextView
    private lateinit var mRotateView: View

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_text_refresh_header, this, true)
        mText = findViewById(R.id.text)
        mRotateView = findViewById(R.id.iv_rotate)
    }

    override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {
        
    }

    override fun onHeaderVisible() {
        mText.text = "下拉刷新"
    }

    override fun onOver() {
        mText.text = "松开刷新"
    }

    override fun onRefresh() {
        mText.text = "正在刷新..."
        val operatingAnim: Animation =
            AnimationUtils.loadAnimation(getContext(), R.anim.rotate_anim)
        val lin = LinearInterpolator()
        operatingAnim.interpolator = lin
        mRotateView.startAnimation(operatingAnim)
    }

    override fun onFinish() {
        mRotateView.clearAnimation()
    }
}