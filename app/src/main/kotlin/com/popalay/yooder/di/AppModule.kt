package com.popalay.yooder.di

import android.app.Application
import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
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
    fun provideFirebase(): DatabaseReference {
        val database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true)
        database.setLogLevel(Logger.Level.DEBUG)
        return database.reference
    }

    @Provides
    @Singleton
    fun provideSocialManager(): SocialManager = VkManager()

    @Provides
    @Singleton
    fun provideDataManager(ref: DatabaseReference): DataManager = FirebaseManager(ref)

    @Provides
    @Singleton
    fun provideEventBus() = PublishSubject.create<Event>()
}