package com.popalay.yooder.mvp.choosefriend

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.pawegio.kandroid.d
import com.popalay.yooder.Application
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.SocialManager
import com.popalay.yooder.models.User
import com.popalay.yooder.mvp.createremind.CreateRemindActivity
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class ChooseFriendPresenter : MvpPresenter<ChooseFriendView>() {

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager

    init {
        Application.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadFriends()
    }

    fun loadFriends() {
        d("loadFriends")
        dataManager.getFriends(socialManager.getMyId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewState.attachFriends(it)
                }
    }

    fun friendChosen(context: Context, friend: User) {
        CreateRemindActivity.open(context, friend.id)
    }
}


