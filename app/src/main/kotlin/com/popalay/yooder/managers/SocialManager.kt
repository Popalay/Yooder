package com.popalay.yooder.managers

import android.app.Activity
import android.content.Intent
import com.popalay.yooder.models.User

interface SocialManager {
    fun login(activity: Activity)
    fun handleLogin(requestCode: Int, resultCode: Int, data: Intent?, callback: (User?, String?) -> Unit):Boolean
    open fun friends(callback: (MutableList<User>) -> Unit)
    open fun getMyId(): String
}