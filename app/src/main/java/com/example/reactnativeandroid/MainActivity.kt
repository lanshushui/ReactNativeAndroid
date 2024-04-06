package com.example.reactnativeandroid

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import com.facebook.react.ReactActivity
import com.facebook.react.ReactActivityDelegate
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.fabricEnabled
import com.facebook.react.defaults.DefaultReactActivityDelegate

class MainActivity : ReactActivity() {

    private var mPreferences: SharedPreferences? = null

    override fun getMainComponentName(): String = "ReactNativeTs"

    override fun createReactActivityDelegate(): ReactActivityDelegate =
        DefaultReactActivityDelegate(this, mainComponentName, fabricEnabled)

    override fun onCreate(savedInstanceState: Bundle?) {
        //连接Metro服务器关键，只需要sp简单保存debug_http_host字段，为当前需要连接的Metro服务器
        mPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        mPreferences?.edit()?.putString("debug_http_host","192.168.0.141:8081")?.apply()
        super.onCreate(savedInstanceState)
    }
}
