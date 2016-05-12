package com.popalay.yooder.mvp.choosefriend

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.popalay.yooder.models.User

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ChooseFriendView : MvpView {

    fun attachFriends(friends: List<User>)

    fun onRemindCreated()
}


