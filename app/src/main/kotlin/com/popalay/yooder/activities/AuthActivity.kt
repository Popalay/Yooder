package com.popalay.yooder.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.popalay.yooder.R
import com.popalay.yooder.eventbus.BusProvider
import com.popalay.yooder.eventbus.LoginButtonEvent
import com.popalay.yooder.eventbus.SignupButtonEvent
import com.popalay.yooder.fragments.LoginFragment
import com.popalay.yooder.fragments.SignupFragment
import com.squareup.otto.Subscribe

public class AuthActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setFragment(LoginFragment(), LoginFragment.TAG)
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            Log.i("Auth", "setFragment $tag")
            supportFragmentManager.beginTransaction().replace(R.id.container, fragment, tag).commit()
        }
    }

    @Subscribe
    public fun onLoginButton(event: LoginButtonEvent) {
        when (event.success) {
            true -> {
                var intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent);
                //startActivity(intentFor<MainActivity>().newTask().clearTop())
                finish()
            }
            else -> setFragment(LoginFragment(), LoginFragment.TAG)
        }
    }

    @Subscribe
    public fun onSignupButton(event: SignupButtonEvent) {
        when (event.email) {
            "" -> setFragment(SignupFragment(), SignupFragment.TAG)
            else -> setFragment(LoginFragment.create(event.email), LoginFragment.TAG)
        }
    }

    override fun onResume() {
        super.onResume()
        BusProvider.instance.register(this)
    }

    override fun onPause() {
        super.onPause()
        BusProvider.instance.unregister(this)
    }
}
