package com.popalay.yooder.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parse.ParseUser
import com.parse.SignUpCallback
import com.popalay.yooder.R
import com.popalay.yooder.eventbus.BusProvider
import com.popalay.yooder.eventbus.LoginButtonEvent
import com.popalay.yooder.eventbus.SignupButtonEvent
import com.popalay.yooder.extensions.hideKeyboard
import com.popalay.yooder.extensions.snackbar
import com.popalay.yooder.managers.DataManager
import kotlinx.android.synthetic.main.fragment_signup.*

public class SignUpFragment : Fragment() {

    companion object {
        val TAG = SignUpFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        btnSignup.setOnClickListener(btnSignUpClick)
        linkLogin.setOnClickListener { BusProvider.instance.post(LoginButtonEvent(false)) }
    }

    private var btnSignUpClick = View.OnClickListener()
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
            signUp(newUser)
        }
    }

    private fun signUp(user: ParseUser) {
        DataManager().signUpUser(user, SignUpCallback { e ->
            if (e == null) {
                // SignUp successful!
                BusProvider.instance.post(SignupButtonEvent(user.email))
                snackbar("Registration successful!")
            } else {
                // Fail!
                snackbar(e.message.toString())
            }
        })
    }
}
