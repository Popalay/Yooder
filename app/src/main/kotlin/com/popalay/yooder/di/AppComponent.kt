package com.popalay.yooder.di

import com.popalay.yooder.activities.AuthActivity
import com.popalay.yooder.activities.ChooseFriendActivity
import com.popalay.yooder.activities.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface  AppComponent {

    fun inject(activity: AuthActivity)

    fun inject(activity: MainActivity)

    fun inject(activity: ChooseFriendActivity)
}