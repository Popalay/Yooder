package com.popalay.yooder.newRemind

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.popalay.yooder.models.User

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface NewRemindView : MvpView {

    fun showError(error: String)

    fun hideError()

    fun onRemindSaved()

    fun attachUser(user: User)
}