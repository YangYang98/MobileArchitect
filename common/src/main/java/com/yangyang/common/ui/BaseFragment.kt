package com.yangyang.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


/**
 * Create by Yang Yang on 2023/11/8
 */
abstract class BaseFragment : Fragment() {

    protected lateinit var layoutView: View

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        layoutView = inflater.inflate(getLayoutId(), container, false)
        return layoutView
    }

}