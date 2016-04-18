package com.popalay.yooder

import android.app.Application
import com.firebase.client.Firebase
import com.popalay.yooder.di.AppComponent
import com.popalay.yooder.di.AppModule
import com.popalay.yooder.di.DaggerAppComponent
import com.vk.sdk.VKSdk

class Application : Application() {

    companion object {
        @JvmStatic lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        Firebase.setAndroidContext(this)
        Firebase.getDefaultConfig().isPersistenceEnabled = true
        VKSdk.initialize(this);
    }
}
