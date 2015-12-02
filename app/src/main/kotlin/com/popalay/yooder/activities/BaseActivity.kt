package com.popalay.yooder.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager

abstract class BaseActivity : AppCompatActivity() {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.i("Dialog", "Click")
        var view = this.currentFocus;
        if (view != null) {
            view.clearFocus()
            var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0);
        }
        return super.onTouchEvent(event)
    }
}
