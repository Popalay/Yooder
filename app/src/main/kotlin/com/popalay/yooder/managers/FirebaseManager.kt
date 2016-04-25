package com.popalay.yooder.managers

import android.util.Log
import com.firebase.client.Firebase
import com.popalay.yooder.models.Remind
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

    override fun getFriends(myId: String): Observable<List<User>> {
        return RxFirebase.getInstance().observeSingleValue(ref.child("users").orderByChild("friends/$myId").equalTo(true))
                .first()
                .subscribeOn(Schedulers.io())
                .flatMap { Observable.from(it.children) }
                .map { it.getValue(User::class.java) }
                .doOnCompleted { Log.d("ddd", "complete") }
                .toSortedList { user, user1 -> user.compareTo(user1) }

    }

    override fun getUser(userId: String): Observable<User> {
        return RxFirebase.getInstance().observeSingleValue(ref.child("users/$userId"))
                .subscribeOn(Schedulers.io())
                .map { it.getValue(User::class.java) }

    }

    override fun saveRemind(remind: Remind) {
        val id = ref.child("reminders").push().key
        remind.id = id
        remind.time = System.currentTimeMillis()
        ref.child("reminders").child(remind.id).setValue(remind)
    }

    override fun getMyRemindersQuery(myId: String) = ref.child("reminders").orderByChild("from").equalTo(myId)
    override fun getMyNotificationsQuery(myId: String) = ref.child("reminders").orderByChild("to").equalTo(myId)
}