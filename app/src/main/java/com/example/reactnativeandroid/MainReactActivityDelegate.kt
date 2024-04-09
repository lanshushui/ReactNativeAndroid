package com.example.reactnativeandroid

import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.reactnativets.MainApplication

class MainReactActivityDelegate(val activity: ReactActivity, mainComponentName: String) :
    ReactActivityDelegate(activity, mainComponentName) {
    override fun loadApp(appKey: String?) {
        (activity.application as MainApplication).loadBusinessJS(MainApplication.JsAction("assets://index.android.bundle") {
            super.loadApp(appKey)
        })
    }
}