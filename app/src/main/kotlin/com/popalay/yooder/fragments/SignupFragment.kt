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
import kotlinx.android.synthetic.fragment_signup.*

public class SignupFragment : Fragment() {

    companion object {
        val TAG = "SignupFragment"
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        btnSignup.setOnClickListener(btnSignupClick)
        linkLogin.setOnClickListener { BusProvider.instance.post(LoginButtonEvent(false)) }
    }

    private var btnSignupClick = View.OnClickListener()
    {
        hideKeyboard();
        var isEmpty = false
        if (name.editText.text.length == 0) {
            name.error = "Full Name cannot be blank"
            isEmpty = true
        } else name.error = ""

        if (email.editText.text.length == 0) {
            email.error = "E-mail cannot be blank"
            isEmpty = true
        } else email.error = ""

        if (password.editText.text.length == 0) {
            password.error = "Password cannot be blank"
            isEmpty = true
        } else password.error = ""

        if (!isEmpty) {
            var fullNameStr = name.editText.text?.toString()
            var emailStr = email.editText.text?.toString()
            var passwordStr = password.editText.text?.toString()

            val newUser = ParseUser()
            newUser.username = emailStr
            newUser.email = emailStr
            newUser.setPassword(passwordStr)
            newUser.put("FullName", fullNameStr)
            signup(newUser)
        }
    }

    private fun signup(user: ParseUser) {
        user.signUpInBackground { e ->
            if (e == null) {
                // Signup successful!
                BusProvider.instance.post(SignupButtonEvent(user.email))
                snackbar("Registration successful!")
            } else {
                // Fail!
                snackbar(e.message.toString())
            }
        }
    }
}
