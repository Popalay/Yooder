package com.popalay.yooder.models

class User(var id: String = "", var name: String = "", var photo: String = "") : Comparable<User> {

    var friends: Map<String, Boolean>? = null

    override fun compareTo(other: User): Int {
        val res = name[0] - other.name[0]
        return if (res > 0) 1 else if (res == 0) 0 else -1
    }
}