package com.popalay.yooder.models


enum class EventType {
    REMIND_CREATED
}

class Event(val type: EventType, val data: Any? = null)