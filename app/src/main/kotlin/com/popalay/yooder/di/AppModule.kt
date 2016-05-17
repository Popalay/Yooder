package com.popalay.yooder.di

import android.app.Application
import android.content.Context
import com.firebase.client.Firebase
import com.firebase.client.Logger
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.FirebaseManager
import com.popalay.yooder.managers.SocialManager
import com.popalay.yooder.managers.VkManager
import com.popalay.yooder.models.Event
import dagger.Module
import dagger.Provides
import rx.subjects.PublishSubject
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideFirebase(context: Context): Firebase {
        Firebase.setAndroidContext(context)
        Firebase.getDefaultConfig().isPersistenceEnabled = true
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.WARN)
        val ref = Firebase("https://yooder.firebaseio.com/")
        ref.keepSynced(true)
        return ref
    }

    @Provides
    @Singleton
    fun provideSocialManager(): SocialManager = VkManager()

    @Provides
    @Singleton
    fun provideDataManager(ref: Firebase): DataManager = FirebaseManager(ref)

    @Provides
    @Singleton
    fun provideEventBus() = PublishSubject.create<Event>()
}