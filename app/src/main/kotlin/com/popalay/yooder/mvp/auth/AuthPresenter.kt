package com.popalay.yooder.mvp.auth

import android.app.Activity
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.popalay.yooder.Application
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.SocialManager
import com.popalay.yooder.models.Event
import rx.subjects.PublishSubject
import javax.inject.Inject


@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager
    @Inject lateinit var eventBus: PublishSubject<Event>

    init {
        Application.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        subscribeForEvents()
    }

    private fun subscribeForEvents() {

    }

    fun login(activity: Activity) {
        socialManager.login(activity)
    }

    fun handleLogin(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        return socialManager.handleLogin(requestCode, resultCode, data) { user, error ->
            if (user != null) {
                dataManager.saveUser(user)
                socialManager.friends { friends ->
                    friends.forEach {
                        dataManager.saveFriend(it, user.id)
                    }
                }
                viewState.loginAccept()
            } else if (error != null) {
                viewState.showError(error)
            }
        }
    }
}