package com.popalay.yooder.managers

import android.util.Log
import com.firebase.client.Firebase
import com.popalay.yooder.models.User
import com.soikonomakis.rxfirebase.RxFirebase
import rx.Observable
import rx.schedulers.Schedulers

class FirebaseManager(val ref: Firebase) : DataManager {

    override fun saveFriend(user: User, myId: String) {
        RxFirebase.getInstance().observeSingleValue(ref.child("users").child(user.id))
                .subscribeOn(Schedulers.io())
                .subscribe { snapshot ->
                    if (!snapshot.exists()) {
                        ref.child("users").child(user.id).setValue(user)
                        ref.child("users").child(user.id).child("friends").child(myId).setValue(true)
                    } else {
                        ref.child("users").child(user.id).child("friends").child(myId).setValue(true)
                    }
                }
    }

    override fun saveUser(user: User) {
        RxFirebase.getInstance().observeSingleValue(ref.child("users").child(user.id))
                .subscribeOn(Schedulers.io())
                .filter { !it.exists() }
                .subscribe {
                    ref.child("users").child(user.id).setValue(user)
                }
    }

    override fun getFriends(myId: String): Observable<User> {
        return RxFirebase.getInstance().observeSingleValue(ref.child("users").orderByChild("friends/$myId").equalTo(true))
                .subscribeOn(Schedulers.io())
                .flatMap { Observable.from(it.children) }
                .doOnNext { Log.d("ddd", it.key) }
                .map { it.getValue(User::class.java) }

    }
}