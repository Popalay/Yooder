package com.popalay.yooder.managers

import com.google.firebase.database.Query
import com.popalay.yooder.models.Remind
import com.popalay.yooder.models.User
import rx.Observable

interface DataManager {
    fun saveUser(user: User)
    fun saveFriend(user: User, myId: String)
    fun getFriends(myId: String): Observable<List<User>>
    fun getUser(userId: String): Observable<User>
    fun saveRemind(remind: Remind)
    fun getMyRemindersQuery(myId: String): Query
    fun getMyNotificationsQuery(myId: String): Query
}