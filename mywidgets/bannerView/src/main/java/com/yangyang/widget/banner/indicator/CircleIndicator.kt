package com.yangyang.widget.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.yangyang.library.utils.DensityUtil
import com.yangyang.widget.banner.R
import com.yangyang.widget.banner.interfaces.IIndicator


/**
 * Create by Yang Yang on 2023/11/16
 */
class CircleIndicator  @JvmOverloads constructor(
    private val context: Context, private val attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr), IIndicator<FrameLayout> {

    @DrawableRes
    private val mPointNormal = R.drawable.shape_point_normal

    @DrawableRes
    private val mPointSelected = R.drawable.shape_point_select

    /**
     * 指示点左右内间距
     */
    private var mPointLeftRightPadding = 0

    /**
     * 指示点上下内间距
     */
    private var mPointTopBottomPadding = 0

    companion object {
        private const val VWC = ViewGroup.LayoutParams.WRAP_CONTENT
    }

    init {
        mPointLeftRightPadding = DensityUtil.dp2px(5f)
        mPointTopBottomPadding = DensityUtil.dp2px(15f)
    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) {
            return
        }

        val groupView = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        var imageView: ImageView
        val imageViewParams = LinearLayout.LayoutParams(VWC, VWC)
        imageViewParams.gravity = Gravity.CENTER_VERTICAL
        imageViewParams.setMargins(
            mPointLeftRightPadding,
            mPointTopBottomPadding,
            mPointLeftRightPadding,
            mPointTopBottomPadding
        )

        for (i in 0 until count) {
            imageView = ImageView(context).apply {
                layoutParams = imageViewParams
                if (i == 0) {
                    setImageResource(mPointSelected)
                } else {
                    setImageResource(mPointNormal)
                }
            }
            groupView.addView(imageView)
        }
        val groupViewParams = LayoutParams(VWC, VWC)
        groupViewParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView, groupViewParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount) {
            val imageView = viewGroup.getChildAt(i) as ImageView
            if (i == current) {
                imageView.setImageResource(mPointSelected)
            } else {
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }
    }
}