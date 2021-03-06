package com.popalay.yooder

import android.app.Application
import com.parse.Parse
import com.parse.ParseObject
import com.popalay.yooder.models.Debt
import com.popalay.yooder.models.Other

public class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Parse.enableLocalDatastore(this)
        ParseObject.registerSubclass(Debt::class.java)
        ParseObject.registerSubclass(Other::class.java)
        Parse.initialize(this, "xIjz6RqkS2yZjhhRWKT2jo0zW0tnVXYiDuDPmHBX", "roIv6s0Tf1dTwyTaHvJd5RMsqBh9xTzLjgA7DiJH")
        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)
    }
}
