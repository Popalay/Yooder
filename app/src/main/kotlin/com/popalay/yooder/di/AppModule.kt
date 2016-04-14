package com.popalay.yooder.di

import android.app.Application
import com.firebase.client.Firebase
import com.firebase.client.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideFirebase(): Firebase {
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.WARN)
        val ref = Firebase("https://yooder.firebaseio.com/")
        ref.keepSynced(true)
        return ref
    }

    @Provides
    @Singleton
    fun provideApplicationContext() = application

}