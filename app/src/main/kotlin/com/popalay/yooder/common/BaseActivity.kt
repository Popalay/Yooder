package com.popalay.yooder.common

import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager

abstract class BaseActivity : MvpAppCompatActivity() {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var view = this.currentFocus;
        if (view != null) {
            view.clearFocus()
            var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0);
        }
        return super.onTouchEvent(event)
    }
}
