package com.popalay.yooder.models

class User() {

    var id: String = ""
    var name: String = ""
    var photo: String = ""
    var friends:Map<String, Boolean> = emptyMap()

    constructor(id: String, name: String, photo: String) : this() {
        this.id = id
        this.name = name
        this.photo = photo
    }
}


