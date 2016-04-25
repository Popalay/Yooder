package com.popalay.yooder.managers

import com.firebase.client.Query
import com.popalay.yooder.models.Remind
import com.popalay.yooder.models.User
import rx.Observable

interface DataManager {
    fun saveUser(user: User)
    fun saveFriend(user: User, myId: String)
    open fun getFriends(myId: String): Observable<List<User>>
    open fun getUser(userId: String): Observable<User>
    open fun saveRemind(remind: Remind)
    open fun getMyRemindersQuery(myId: String): Query
    open fun getMyNotificationsQuery(myId: String): Query
}