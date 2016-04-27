package com.popalay.yooder.models

class Remind() : Comparable<Remind> {

    override fun compareTo(other: Remind): Int {
        var res = time - other.time
        return if (res > 0) 1 else if (res == 0L) 0 else -1
    }

    var id: String = ""
    var message: String = ""
    var from: String = ""
    var to: String = ""
    var priority:Int = 0
    var time: Long = 0L

    constructor(message: String, from: String, to: String, priority:Int) : this() {
        this.message = message
        this.from = from
        this.to = to
        this.priority = priority
    }
}