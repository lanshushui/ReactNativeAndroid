package com.example.reactnativeandroid

import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate

class MainReactActivityDelegate(activity: ReactActivity, mainComponentName: String) :
    ReactActivityDelegate(activity, mainComponentName) {
    override fun loadApp(appKey: String?) {
        ReactJSLoader.loadBusinessJS(JsAction("assets://index.android.bundle") {
            super.loadApp(appKey)
        })
    }
}