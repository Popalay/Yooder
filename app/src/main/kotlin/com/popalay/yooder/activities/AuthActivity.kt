package com.popalay.yooder.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.firebase.client.Firebase
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.extensions.snackbar
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
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
    }

    private fun initUI() {
        Log.d("dd", "initUi")
        btnLogin.setOnClickListener {
            VKSdk.login(this, VKScope.FRIENDS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken?> {
            override fun onResult(res: VKAccessToken?) {
                Log.d("dd", "login")
                toMain()
            }

            override fun onError(error: VKError?) {
                snackbar(coordinator, error?.errorMessage as String, Snackbar.LENGTH_LONG).show()
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
