package com.yangyang.library.utils

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import java.lang.ref.WeakReference


/**
 * Create by Yang Yang on 2023/12/11
 */
class ActivityManager private constructor(){

    private val activityRefs = arrayListOf<WeakReference<Activity>>()
    private val frontBackCallbacks = arrayListOf<FrontBackCallback>()

    private var activityStartCount = 0
    private var isFront = true

    val topActivity: Activity?
        get() {
            return if (activityRefs.size <= 0) {
                null
            } else {
                activityRefs[activityRefs.size - 1].get()
            }
        }

    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    fun addFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallbacks.add(callback)
    }

    fun removeFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallbacks.remove(callback)
    }

    companion object {

        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }

    interface FrontBackCallback {
        fun onChanged(front: Boolean)
    }

    private fun onFrontBackChanged(front: Boolean) {
        frontBackCallbacks.forEach {
            it.onChanged(front)
        }
    }

    inner class InnerActivityLifecycleCallbacks: ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            //activityStartCount > 0说明应用处在可见状态，也就是前台
            //!isFront 表示之前是不是在后台
            if (!isFront && activityStartCount > 0) {
                isFront = true
                onFrontBackChanged(isFront)
            }
        }

        override fun onActivityResumed(activity: Activity) {
            
        }

        override fun onActivityPaused(activity: Activity) {
            
        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (isFront && activityStartCount <= 0) {
                isFront = false
                onFrontBackChanged(isFront)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            
        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef.get() == activity) {
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }

    }
}