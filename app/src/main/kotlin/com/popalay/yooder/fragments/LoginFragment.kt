package com.popalay.yooder.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parse.ParseUser
import com.popalay.yooder.R
import com.popalay.yooder.eventbus.BusProvider
import com.popalay.yooder.eventbus.LoginButtonEvent
import com.popalay.yooder.eventbus.SignupButtonEvent
import com.popalay.yooder.extensions.hideKeyboard
import com.popalay.yooder.extensions.snackbar
import kotlinx.android.synthetic.fragment_login.btnLogin
import kotlinx.android.synthetic.fragment_login.email
import kotlinx.android.synthetic.fragment_login.linkSignup
import kotlinx.android.synthetic.fragment_login.password

public class LoginFragment : Fragment() {

    companion object {
        val TAG = "LoginFragment"

        fun create(email: String): LoginFragment {
            var loginFragment = LoginFragment()
            var args = Bundle()
            args.putString("email", email)
            loginFragment.arguments = args
            return loginFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        email.editText.setText(arguments?.getString("email", ""))
        linkSignup.setOnClickListener { BusProvider.instance.post(SignupButtonEvent()) }
        btnLogin.setOnClickListener(btnLoginClick)
    }

    private var btnLoginClick = View.OnClickListener()
    {
        hideKeyboard();
        var isEmpty = false
        if (email.editText.text.length == 0) {
            email.error = "E-mail cannot be blank"
            isEmpty = true
        } else email.error = ""

        if (password.editText.text.length == 0) {
            password.error = "Password cannot be blank"
            isEmpty = true
        } else password.error = ""

        if (!isEmpty) {
            var emailStr = email.editText.text.toString()
            var passwordStr = password.editText.text.toString()

            val newUser = ParseUser()
            newUser.email = emailStr
            newUser.setPassword(passwordStr)
            login(emailStr, passwordStr)
        }
    }

    private fun login(userName: String, password: String) {
        ParseUser.logInInBackground(userName, password, { user, e ->
            if (user != null) {
                // Hooray! The user is logged in.
                BusProvider.instance.post(LoginButtonEvent(true))
                snackbar("Welcome ${user.getString("FullName")}!")
            } else {
                // Login failed!
                snackbar(e.message.toString())
            }
        })
    }
}

