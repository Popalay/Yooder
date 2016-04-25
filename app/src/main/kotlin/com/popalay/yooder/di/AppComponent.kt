package com.popalay.yooder.di

import com.popalay.yooder.activities.AuthActivity
import com.popalay.yooder.activities.ChooseFriendActivity
import com.popalay.yooder.activities.InputMessageActivity
import com.popalay.yooder.activities.MainActivity
import com.popalay.yooder.fragments.NotificationsFragment
import com.popalay.yooder.lists.RemindersAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface  AppComponent {

    fun inject(activity: AuthActivity)

    fun inject(activity: MainActivity)

    fun inject(activity: ChooseFriendActivity)

    fun inject(activity: InputMessageActivity)

    fun inject(adapter: RemindersAdapter)

    fun inject(fragment: NotificationsFragment)
}