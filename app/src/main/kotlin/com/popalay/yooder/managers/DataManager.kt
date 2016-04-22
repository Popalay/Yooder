package com.popalay.yooder.managers

import com.popalay.yooder.models.User
import rx.Observable

interface DataManager {
    fun saveUser(user: User)
    fun saveFriend(user: User, myId: String)
    open fun getFriends(myId: String): Observable<List<User>>
}