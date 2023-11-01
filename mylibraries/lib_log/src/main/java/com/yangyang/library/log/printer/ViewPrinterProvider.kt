package com.yangyang.library.log.printer

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yangyang.library.utils.DensityUtil


/**
 * Create by Yang Yang on 2023/11/1
 */
class ViewPrinterProvider(private val rootView: FrameLayout, private val recyclerView: RecyclerView) {

    private var floatingView: View? = null
    private var isOpen: Boolean = false
    private var logView: FrameLayout? = null

    companion object {
        private const val TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW"
        private const val TAG_LOG_VIEW = "TAG_LOG_VIEW"
    }

    fun showFloatingView() {
        if (rootView.findViewWithTag<View>(TAG_FLOATING_VIEW) != null) {
            return
        }
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.BOTTOM or Gravity.END
            bottomMargin = DensityUtil.dp2px(100f)
        }
        val floatingView = getFloatingView().apply {
            tag = TAG_FLOATING_VIEW
            setBackgroundColor(Color.BLACK)
            alpha = 0.8f
        }
        rootView.addView(floatingView, params)
    }

    fun closeFloatingView() {
        rootView.removeView(getFloatingView())
    }

    fun showLogView() {
        if (rootView.findViewWithTag<View>(TAG_LOG_VIEW) != null) {
            return
        }

        val params = LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dp2px(160f)).apply {
            gravity = Gravity.BOTTOM
        }
        val logView = getLogView().apply {
            tag = TAG_LOG_VIEW
        }
        rootView.addView(logView, params)
        isOpen = true
    }

    fun closeLogView() {
        isOpen = false
        rootView.removeView(getLogView())
    }

    private fun getFloatingView(): View {
        if (floatingView != null) {
            return floatingView!!
        }
        val textView = TextView(rootView.context).apply {
            setOnClickListener {
                if (!isOpen) {
                    showLogView()
                }
            }
            text = "MyLog"
            setTextColor(Color.WHITE)
        }
        floatingView = textView
        return floatingView!!
    }

    private fun getLogView(): View {
        if (logView != null) {
            return logView!!
        }
        val logView = FrameLayout(rootView.context).apply {
            setBackgroundColor(Color.BLACK)
            addView(recyclerView)
        }
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.END
        }
        val closeView = TextView(rootView.context).apply {
            setOnClickListener {
                closeLogView()
            }
            text = "close"
            setTextColor(Color.WHITE)
        }
        logView.addView(closeView, params)
        this.logView = logView

        return this.logView!!
    }
}