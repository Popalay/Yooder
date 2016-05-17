package com.popalay.yooder.di

import com.popalay.yooder.fragments.FeedFragment
import com.popalay.yooder.fragments.NotificationsFragment
import com.popalay.yooder.lists.RemindersAdapter
import com.popalay.yooder.mvp.auth.AuthPresenter
import com.popalay.yooder.mvp.choosefriend.ChooseFriendPresenter
import com.popalay.yooder.mvp.createremind.CreateRemindPresenter
import com.popalay.yooder.mvp.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(adapter: RemindersAdapter)

    fun inject(fragment: FeedFragment)

    fun inject(fragment: NotificationsFragment)

    fun inject(any: CreateRemindPresenter)

    fun inject(any: ChooseFriendPresenter)

    fun inject(any: AuthPresenter)

    fun inject(any: MainPresenter)
}