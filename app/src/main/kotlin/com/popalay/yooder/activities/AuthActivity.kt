package com.popalay.yooder.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.extensions.snackbar
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.SocialManager
import com.vk.sdk.VKSdk
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import javax.inject.Inject

class AuthActivity : BaseActivity(){

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Application.graph.inject(this)
        setContentView(R.layout.activity_auth)
        init()
        initUI()
    }

    private inline fun init() {
        Log.d("dd", VKSdk.isLoggedIn().toString());
    }

    private inline fun initUI() {
        Log.d("dd", "initUi")
        btnLogin.setOnClickListener {
            socialManager.login(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!socialManager.handleLogin(requestCode, resultCode, data) { user, error ->
            if (user != null) {
                dataManager.saveUser(user)
                socialManager.friends { friends ->
                    friends.forEach {
                        dataManager.saveUser(it)
                    }
                }
                toMain()
            } else if (error != null) {
                snackbar(coordinator, error, Snackbar.LENGTH_LONG).show()
            }
        }) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private inline fun toMain() {
        startActivity(intentFor<MainActivity>().newTask().clearTop())
        finish()
    }

}
