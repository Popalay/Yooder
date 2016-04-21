package com.popalay.yooder.managers

import com.firebase.client.Firebase
import com.popalay.yooder.models.User
import com.soikonomakis.rxfirebase.RxFirebase
import rx.schedulers.Schedulers

class FirebaseManager(val ref: Firebase) : DataManager {

    override fun saveUser(user: User) {
        RxFirebase.getInstance().observeSingleValue(ref.child("users").child(user.id))
                .subscribeOn(Schedulers.io())
                .filter { snapshot ->
                    !snapshot.exists()
                }.subscribe { snapshot ->
            ref.child("users").child(user.id).setValue(user)
        }
    }
}