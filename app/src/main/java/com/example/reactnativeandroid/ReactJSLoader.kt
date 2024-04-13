package com.example.reactnativeandroid

import android.app.Application
import android.content.res.AssetManager
import com.facebook.react.ReactApplication
import com.facebook.react.ReactInstanceManager
import com.facebook.react.ReactNativeHost
import com.facebook.react.bridge.CatalystInstance
import com.facebook.react.bridge.ReactContext
import com.facebook.soloader.SoLoader

object ReactJSLoader {

    lateinit var application: Application

    lateinit var reactNativeHost: ReactNativeHost

    val reactInstanceManager: ReactInstanceManager
        get() = reactNativeHost.reactInstanceManager

    val reactContext: ReactContext?
        get() = reactInstanceManager.currentReactContext

    val catalystInstance: CatalystInstance?
        get() = reactContext?.catalystInstance

    val assets: AssetManager
        get() = application.assets

    var reactContextInit = false
        private set

    private val businessBundleList = mutableListOf<JsAction>()

    public fun init(application: Application) {
        SoLoader.init(application, false)
        this.application = application
        reactNativeHost = (application as ReactApplication).reactNativeHost
        reactInstanceManager.addReactInstanceEventListener { contect ->
            reactContextInit = true
            scheduleJSAction(contect.catalystInstance)
        }
        //创建reactContext，开始加载common.js
        createReactContextInBackground()
    }

    private fun createReactContextInBackground() {
        reactNativeHost.reactInstanceManager?.createReactContextInBackground()
    }


    public fun loadBusinessJS(action: JsAction) {
        if (reactContextInit) {
            catalystInstance?.loadScriptFromAssets(
                assets,
                action.bundle,
                true
            )
            action.cb()
        } else {
            businessBundleList.add(action)
        }
    }


    private fun scheduleJSAction(catalystInstance: CatalystInstance?) {
        businessBundleList.forEach {
            catalystInstance?.loadScriptFromAssets(assets, it.bundle, false)
            it.cb()
        }
        businessBundleList.clear()
    }

}

data class JsAction(val bundle: String, val cb: () -> Unit)