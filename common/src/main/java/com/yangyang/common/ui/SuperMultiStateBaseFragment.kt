package com.yangyang.common.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.ViewUtils
import com.yangyang.common.ui.widgets.MultiStateView

/**
 * @ClassName SuperMultiStateBaseFragment.kt
 * @Description
 * @Author yang
 * @Date 2023/5/15 10:01
 * @Version 1.0
 *
 * TODO 封装的不好，还需要改进 @Link https://github.com/zhangnangua/grocery-store/blob/43d4f5940c3b940162a9867b8d5ced44861c6be6/Applets_Container/base/mvvm/src/main/java/com/pumpkin/mvvm/view/SuperMultiStateBaseFragment.kt#L38
 */
abstract class SuperMultiStateBaseFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    open fun setBaseContentForState(
        view: View,
        state: MultiStateView.ViewState,
        switchToState: Boolean = false
    ) {
        (layoutView as? MultiStateView)?.addViewByViewState(view, state, switchToState)
    }

    fun switchState(state: MultiStateView.ViewState) {
        (layoutView as? MultiStateView)?.currentState = state
    }

    fun getViewByState(state: MultiStateView.ViewState) =
        (layoutView as? MultiStateView)?.obtainView(state)

    fun setContentView(v: View) {
        setBaseContentForState(v, MultiStateView.ViewState.CONTENT, true)
    }

    abstract fun initView(view: View)
}