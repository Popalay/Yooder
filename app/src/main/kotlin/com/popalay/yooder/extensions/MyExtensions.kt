package com.popalay.yooder.extensions

import android.content.Context
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager

fun Fragment.hideKeyboard() {
    if (activity != null && activity.window != null && activity.window.decorView != null) {
        var imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager ;
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0);
    }
}