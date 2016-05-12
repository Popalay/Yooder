package com.popalay.yooder.di

import com.popalay.yooder.activities.AuthActivity
import com.popalay.yooder.activities.MainActivity
import com.popalay.yooder.fragments.FeedFragment
import com.popalay.yooder.fragments.NotificationsFragment
import com.popalay.yooder.lists.RemindersAdapter
import com.popalay.yooder.mvp.choosefriend.ChooseFriendPresenter
import com.popalay.yooder.mvp.createremind.CreateRemindActivity
import com.popalay.yooder.mvp.createremind.CreateRemindPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(activity: AuthActivity)

    fun inject(activity: MainActivity)

    fun inject(activity: CreateRemindActivity)

    fun inject(adapter: RemindersAdapter)

    fun inject(fragment: FeedFragment)

    fun inject(fragment: NotificationsFragment)

    fun inject(any: CreateRemindPresenter)

    fun inject(any: ChooseFriendPresenter)
}