package com.popalay.yooder.managers

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.pawegio.kandroid.d
import com.popalay.yooder.models.Remind
import com.popalay.yooder.models.User
import com.popalay.yooder.utils.RxFirebase
import rx.Observable
import rx.schedulers.Schedulers

class FirebaseManager(val ref: DatabaseReference) : DataManager {

    override fun saveFriend(user: User, myId: String) {
        RxFirebase.observeSingleValue(ref.child("users").child(user.id))
                .subscribeOn(Schedulers.io())
                .subscribe { userRef ->
                    if (userRef != null) {
                        ref.child("users").child(user.id).setValue(user)
                        ref.child("users").child(user.id).child("friends").child(myId).setValue(true)
                    } else {
                        ref.child("users").child(user.id).child("friends").child(myId).setValue(true)
                    }
                }
    }

    override fun saveUser(user: User) {
        RxFirebase.observeSingleValue(ref.child("users").child(user.id))
                .subscribeOn(Schedulers.io())
                .filter { it == null }
                .subscribe {
                    d("sss")
                    ref.child("users").child(user.id).setValue(user)
                }
    }

    override fun getFriends(myId: String): Observable<List<User>> {
        return RxFirebase.observe(ref.child("users").orderByChild("friends/$myId").equalTo(true))
                .first()
                .subscribeOn(Schedulers.io())
                .flatMap { Observable.from(it.children) }
                .map { it.getValue(User::class.java) }
                .doOnCompleted { Log.d("ddd", "complete") }
                .toSortedList { user, user1 -> user.compareTo(user1) }


    }

    override fun getUser(userId: String): Observable<User> {
        return RxFirebase.observeSingleValue(ref.child("users/$userId"))
                .subscribeOn(Schedulers.io())
                .map { it.getValue(User::class.java) }

    }

    override fun saveRemind(remind: Remind) {
        val id = ref.child("reminders").push().key
        remind.id = id
        remind.time = System.currentTimeMillis()
        ref.child("reminders").child(remind.id).setValue(remind)
        ref.child("reminders").child(remind.id).setPriority(0 - remind.time)
    }

    override fun getMyRemindersQuery(myId: String) = ref.child("reminders").orderByChild("from").equalTo(myId).ref

    override fun getMyNotificationsQuery(myId: String) = ref.child("reminders").orderByChild("to").equalTo(myId.toDouble())
}