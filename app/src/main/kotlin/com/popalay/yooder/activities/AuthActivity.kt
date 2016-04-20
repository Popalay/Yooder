package com.popalay.yooder.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.firebase.client.Firebase
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.extensions.snackbar
import com.popalay.yooder.models.User
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import com.vk.sdk.api.model.VKApiUser
import com.vk.sdk.api.model.VKList
import com.vk.sdk.util.VKUtil
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import java.util.*
import javax.inject.Inject

class AuthActivity : BaseActivity(), AnkoLogger {

    @Inject lateinit var ref: Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        Application.graph.inject(this)
        init()
        initUI()
    }

    private fun init() {
        Log.d("dd", VKSdk.isLoggedIn().toString());
        logger.info(Arrays.toString(VKUtil.getCertificateFingerprint(this, this.packageName)))
    }

    private fun initUI() {
        Log.d("dd", "initUi")
        btnLogin.setOnClickListener {
            VKSdk.login(this, VKScope.FRIENDS, VKScope.PHOTOS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken?> {
            override fun onResult(res: VKAccessToken?) {
                Log.d("dd", "login")
                VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
                        "id,first_name,last_name,photo_200")).executeWithListener(object : VKRequest.VKRequestListener() {
                    override fun onComplete(response: VKResponse?) {
                        super.onComplete(response)
                        val vkUser = (response?.parsedModel as VKList<*>)[0] as VKApiUser
                        val user = User(vkUser.id.toString(), vkUser.toString(), vkUser.photo_200)
                        ref.child("users").child(user.id).setValue(user)
                    }
                })
                toMain()
            }

            override fun onError(error: VKError?) {
                error?.errorMessage ?: snackbar(coordinator, error?.errorMessage!!, Snackbar.LENGTH_LONG).show()
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


    private fun toMain() {
        startActivity(intentFor<MainActivity>().newTask().clearTop())
        finish()
    }

}
