package com.popalay.yooder.managers

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.popalay.yooder.models.User
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList
import java.util.*

class VkManager : SocialManager {

    override fun login(activity: Activity) {
        VKSdk.login(activity, VKScope.FRIENDS, VKScope.PHOTOS)
    }

    override fun handleLogin(requestCode: Int, resultCode: Int, data: Intent?, callback: (User?, String?) -> Unit): Boolean {
        return VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken?> {
            override fun onResult(res: VKAccessToken?) {
                Log.d("dd", "login")
                VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                        "id,first_name,last_name,photo_100")).executeWithListener(object : VKRequest.VKRequestListener() {
                    override fun onComplete(response: VKResponse?) {
                        super.onComplete(response)
                        val vkUser = (response?.parsedModel as VKList<*>)[0] as VKApiUser
                        val user = User(vkUser.id.toString(), vkUser.toString(), vkUser.photo_100)
                        callback.invoke(user, null)
                    }
                })
            }

            override fun onError(error: VKError?) {
                callback.invoke(null, error?.errorMessage)
            }
        })
    }

    override fun friends(callback: (MutableList<User>) -> Unit) {
        VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "id,first_name,last_name,photo_100"))
                .executeWithListener(object : VKRequest.VKRequestListener() {
                    override fun onComplete(response: VKResponse?) {
                        super.onComplete(response)
                        val vkUsers = response?.parsedModel as VKList<VKApiUser>
                        var users = ArrayList<User>()
                        vkUsers.map {
                            User(it.id.toString(), it.toString(), it.photo_100)
                        }.forEach { user ->
                            users.add(user)
                        }
                        callback.invoke(users)
                    }
                })
    }
}