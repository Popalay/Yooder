package com.popalay.yooder.mvp.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.popalay.yooder.models.User


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView : MvpView {

    fun onReturnCurrentUser(user: User)

    fun navigateToAuthScreen()

    fun openChooseFriendScreen()

    fun onPageSelected(position: Int, animate:Boolean)
}