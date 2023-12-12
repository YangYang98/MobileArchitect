package com.yangyang.mobile.architect

import android.os.Bundle
import android.widget.Toast
import com.yangyang.common.ui.BaseActivity
import com.yangyang.library.utils.ActivityManager
import com.yangyang.mobile.architect.logic.MainActivityLogic

class MainActivity : BaseActivity(), MainActivityLogic.ActivityProvider {

    private lateinit var activityLogic: MainActivityLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityLogic = MainActivityLogic(this, savedInstanceState)

        ActivityManager.instance.addFrontBackCallback(object : ActivityManager.FrontBackCallback {
            override fun onChanged(front: Boolean) {
                Toast.makeText(applicationContext, "当前处于:${if (front) "前台" else "后台"}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        activityLogic.onSaveInstanceState(outState)
    }
}