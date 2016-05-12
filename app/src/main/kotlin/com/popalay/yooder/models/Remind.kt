package com.popalay.yooder.models

import kotlin.properties.Delegates

class Remind(var message: String = "", var from: String = "", var to: String = "", var priority: Int = 0) : Comparable<Remind> {

    var id: String by Delegates.notNull<String>()
    var time: Long by Delegates.notNull<Long>()

    override fun compareTo(other: Remind): Int {
        var res = time - other.time
        return if (res > 0) 1 else if (res == 0L) 0 else -1
    }
}