package com.popalay.yooder.activities

import android.content.Context
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import java.util.logging.Logger

abstract class BaseActivity : RxAppCompatActivity() {

    val logger = Logger.getLogger(this.javaClass.simpleName)

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
