package com.yangyang.common.ui.tab

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yangyang.library.tab.bottom.TabBottomInfo


/**
 * Create by Yang Yang on 2023/11/8
 */
class TabViewAdapter(private val fragmentManager: FragmentManager, private val mInfoList: List<TabBottomInfo<*>>) {

    var mCurFragment: Fragment? = null
        private set

    fun instantiateItem(container: View, position: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (mCurFragment != null) {
            fragmentTransaction.hide(mCurFragment!!)
        }

        val name = "${container.id}:$position"
        var fragment = fragmentManager.findFragmentByTag(name)
        if (fragment != null) {
            fragmentTransaction.show(fragment)
        } else {
            fragment = getItem(position)
            if (fragment?.isAdded == false) {
                fragmentTransaction.add(container.id, fragment, name)
            }
        }
        mCurFragment = fragment

        fragmentTransaction.commit()
    }

    private fun getItem(position: Int): Fragment? {
        return try {
            mInfoList[position].fragment?.newInstance()
        } catch (e: Exception) {
            null
        }
    }

    fun getCount(): Int {
        return mInfoList.size
    }
}