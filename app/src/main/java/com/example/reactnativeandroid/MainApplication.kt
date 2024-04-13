package com.example.reactnativeandroid

import android.app.Application
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.shell.MainReactPackage
import java.util.Arrays

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

    override fun onCreate() {
        super.onCreate()
        ReactJSLoader.init(this)
    }
}
