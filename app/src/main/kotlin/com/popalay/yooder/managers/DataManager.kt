package com.popalay.yooder.managers

import android.util.Log
import com.parse.ParseUser
import com.parse.SignUpCallback
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.parse.ParseObservable
import rx.schedulers.Schedulers

class DataManager {

    fun getUser(): ParseUser = ParseUser.getCurrentUser()

    fun signUpUser(user: ParseUser, callback: SignUpCallback) {
        return user.signUpInBackground(callback)
    }

    fun loginUser(userName: String, password: String): Observable<ParseUser> {
        return ParseObservable.logIn(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun findUser(userName: String): Observable<ParseUser> {
        Log.d("Find", userName)
        return ParseObservable.find(ParseUser.getQuery().whereContains("name", userName))
    }
}