package com.popalay.yooder.mvp.createremind

import android.text.TextUtils
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.popalay.yooder.Application
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.SocialManager
import com.popalay.yooder.models.Remind
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@InjectViewState
class CreateRemindPresenter : MvpPresenter<CreateRemindView>() {

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager

    init {
        Application.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun getUser(id: String) {
        dataManager.getUser(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ viewState.attachUser(it) }, { e -> e.printStackTrace() })
    }

    fun saveRemind(to: String, message: String, priority: Int) {
        if (TextUtils.isEmpty(message)) {
            viewState.showError("Can't be empty")
        } else {
            viewState.hideError()
            dataManager.saveRemind(Remind(message, socialManager.getMyId(), to, priority))
            viewState.onRemindSaved()
        }
    }

}

