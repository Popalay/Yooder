package com.popalay.yooder.models

class User() : Comparable<User> {

    override fun compareTo(other: User): Int {
        var res = name[0] - other.name[0]
        return if (res > 0) 1 else if (res == 0) 0 else -1
    }

    var id: String = ""
    var name: String = ""
    var photo: String = ""
    var friends: Map<String, Boolean> = emptyMap()

    constructor(id: String, name: String, photo: String) : this() {
        this.id = id
        this.name = name
        this.photo = photo
    }
}


