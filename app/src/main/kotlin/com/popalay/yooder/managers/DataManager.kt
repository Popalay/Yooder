package com.popalay.yooder.managers

import com.popalay.yooder.models.User

interface DataManager {

    fun saveUser(user: User)
}