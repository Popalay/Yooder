package com.popalay.yooder.mvp.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.popalay.yooder.Application
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.SocialManager
import com.popalay.yooder.models.Event
import com.vk.sdk.VKSdk
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager
    @Inject lateinit var eventBus: PublishSubject<Event>

    init {
        Application.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        subscribeForEvents()
        checkAuth()
    }

    private fun subscribeForEvents() {

    }

    private fun checkAuth() {
        if (!socialManager.isLogged()) {
            viewState.navigateToAuthScreen()
        } else {
            dataManager.getUser(socialManager.getMyId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { viewState.onReturnCurrentUser(it) }
        }
    }

    fun logout() {
        VKSdk.logout()
        viewState.navigateToAuthScreen()
    }

    fun addRemind() {
        viewState.openChooseFriendScreen()
    }

    fun selectPage(position: Int) {
        viewState.onPageSelected(position)
    }
}