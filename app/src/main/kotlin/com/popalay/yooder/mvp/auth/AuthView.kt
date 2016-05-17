package com.popalay.yooder.mvp.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface AuthView : MvpView {

    fun showError(message: String)
    fun loginAccept()
}

