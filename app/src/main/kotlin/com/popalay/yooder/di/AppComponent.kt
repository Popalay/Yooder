package com.popalay.yooder.di

import com.popalay.yooder.activities.AuthActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface  AppComponent {

    fun inject(authActivity: AuthActivity)
}