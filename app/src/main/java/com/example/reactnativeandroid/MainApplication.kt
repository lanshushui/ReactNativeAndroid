package com.reactnativets

import android.app.Application
import android.os.Looper
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactInstanceEventListener
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.CatalystInstance
import com.facebook.react.bridge.ReactContext
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.shell.MainReactPackage
import com.facebook.soloader.SoLoader
import java.util.Arrays
import java.util.logging.Handler

class MainApplication : Application(), ReactApplication {

    override val reactNativeHost: ReactNativeHost =
        object : DefaultReactNativeHost(this) {

            override fun getPackages(): List<ReactPackage> =
                ArrayList<ReactPackage>(
                    Arrays.asList<ReactPackage>(
                        MainReactPackage(null)
                    )
                )

            override fun getJSMainModuleName(): String = "index"

            override fun getUseDeveloperSupport(): Boolean = true

            override val isNewArchEnabled: Boolean = true

            override val isHermesEnabled: Boolean = false

            override fun getBundleAssetName() = "common.android.bundle"
        }

    override val reactHost: ReactHost
        get() = getDefaultReactHost(this.applicationContext, reactNativeHost)

    var reactContextInit = false
        private set

    private val businessBundleList = mutableListOf<JsAction>()

    public fun loadBusinessJS(action: JsAction) {
        if (reactContextInit) {
            reactNativeHost.reactInstanceManager?.currentReactContext?.catalystInstance?.loadScriptFromAssets(
                assets,
                action.bundle,
                true
            )
            action.cb()
        } else {
            businessBundleList.add(action)
        }
    }

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)
        reactNativeHost.reactInstanceManager.addReactInstanceEventListener { contect ->
            reactContextInit = true
            scheduleJSAction(contect?.catalystInstance)
        }
        reactNativeHost.reactInstanceManager?.createReactContextInBackground()
    }

    private fun scheduleJSAction(catalystInstance: CatalystInstance?) {
        businessBundleList.forEach {
            catalystInstance?.loadScriptFromAssets(assets, it.bundle, false)
            it.cb()
        }
        businessBundleList.clear()
    }

    data class JsAction(val bundle: String, val cb: () -> Unit)
}
