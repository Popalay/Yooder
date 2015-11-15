package com.popalay.yooder.eventbus

import com.squareup.otto.Bus

public object BusProvider {
    public val instance: Bus = Bus()
}