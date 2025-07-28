package com.yangyang.mobile.architect.fragment

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.yangyang.common.ui.BaseFragment
import com.yangyang.common.ui.SuperMultiStateBaseFragment
import com.yangyang.common.ui.widgets.MultiStateView
import com.yangyang.mobile.architect.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * Create by Yang Yang on 2023/11/8
 */
class HomePageFragment : SuperMultiStateBaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        fillView()
        switchState(MultiStateView.ViewState.LOADING)
        lifecycleScope.launch {
            delay(2000)
            switchState(MultiStateView.ViewState.CONTENT)
        }
    }

    private fun fillView() {
        //setBaseContentForState(loadingView, MultiStateView.ViewState.LOADING)
        //setContentView(contentView)
    }
}