package com.popalay.yooder.mvp.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.startActivity
import com.popalay.yooder.R
import com.popalay.yooder.common.BaseActivity
import com.popalay.yooder.extensions.snackbar
import com.popalay.yooder.mvp.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class AuthActivity : BaseActivity(), AuthView {
    @InjectPresenter lateinit var presenter: AuthPresenter

    companion object {

        fun open(context: Context) {
            context.startActivity<AuthActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        initUI()
    }

    private fun initUI() {
        btnLogin.setOnClickListener {
            presenter.login(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!presenter.handleLogin(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showError(message: String) {
        snackbar(coordinator, message, Snackbar.LENGTH_LONG).show()
    }

    override fun loginAccept() {
        startActivity(intentFor<MainActivity>().newTask().clearTop())
        finish()
    }

}
