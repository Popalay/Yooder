package com.popalay.yooder.managers

import com.google.firebase.database.DatabaseReference
import com.kelvinapps.rxfirebase.RxFirebaseDatabase
import com.popalay.yooder.models.Remind
import com.popalay.yooder.models.User
import rx.Observable
import rx.schedulers.Schedulers

class FirebaseManager(val ref: DatabaseReference) : DataManager {

    override fun saveFriend(user: User, myId: String) {
        RxFirebaseDatabase.observeSingleValue(ref.child("users").child(user.id), User::class.java)
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
        RxFirebaseDatabase.observeSingleValue(ref.child("users").child(user.id), User::class.java)
                .subscribeOn(Schedulers.io())
                .filter { it == null }
                .subscribe {
                    ref.child("users").child(user.id).setValue(user)
                }
    }

    override fun getFriends(myId: String): Observable<List<User>> {
        return RxFirebaseDatabase.observeValuesList(ref.child("users").orderByChild("friends/$myId").equalTo(true), User::class.java)
                .first()
                .subscribeOn(Schedulers.io())

    }

    override fun getUser(userId: String): Observable<User> {
        return RxFirebaseDatabase.observeSingleValue(ref.child("users/$userId"), User::class.java)
                .subscribeOn(Schedulers.io())

    }

    override fun saveRemind(remind: Remind) {
        val id = ref.child("reminders").push().key
        remind.id = id
        remind.time = System.currentTimeMillis()
        ref.child("reminders").child(remind.id).setValue(remind)
        ref.child("reminders").child(remind.id).setPriority(0 - remind.time)
    }

    override fun getMyRemindersQuery(myId: String) = ref.child("reminders").orderByChild("from").equalTo(myId)

    override fun getMyNotificationsQuery(myId: String) = ref.child("reminders").orderByChild("to").equalTo(myId.toDouble())
}