package com.popalay.yooder.extensions

import android.app.Activity
import android.os.Build
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import java.util.concurrent.atomic.AtomicInteger

fun View.snackbar(text: CharSequence, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar {
    val snack = Snackbar.make(this, text, duration)
    snack.init()
    snack.show()
    return snack
}

fun View.snackbar(@StringRes()text: Int, duration: Int = Snackbar.LENGTH_SHORT, init: Snackbar.() -> Unit = {}): Snackbar {
    val snack = Snackbar.make(this, text, duration)
    snack.init()
    snack.show()
    return snack
}

fun Fragment.snackbar(text : CharSequence, duration: Int = Snackbar.LENGTH_SHORT, init : Snackbar.() -> Unit = {}) : Snackbar {
    return view!!.snackbar(text, duration, init)
}

fun Fragment.snackbar(@StringRes() text : Int, duration: Int = Snackbar.LENGTH_SHORT, init : Snackbar.() -> Unit = {}) : Snackbar {
    return view!!.snackbar(text, duration, init)
}

fun Activity.snackbar(view: View, text : CharSequence, duration: Int = Snackbar.LENGTH_SHORT, init : Snackbar.() -> Unit = {}) : Snackbar {
    return view.snackbar(text, duration, init)
}

fun Activity.snackbar(view: View, @StringRes() text : Int, duration: Int = Snackbar.LENGTH_SHORT, init : Snackbar.() -> Unit = {}) : Snackbar {
    return view.snackbar(text, duration, init)
}

private object ViewCounter {
    private val viewCounter = AtomicInteger(1)
    public fun generateViewId(): Int {
        while (true) {
            val result = viewCounter.get()
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            var newValue = result + 1
            if (newValue > 16777215) newValue = 1 // Roll over to 1, not 0.
            if (viewCounter.compareAndSet(result, newValue)) {
                return result
            }
        }
    }
}

fun View.generateViewIdCompat(): Int {
    if (Build.VERSION.SDK_INT >= 19)
        return View.generateViewId()
    else
        return ViewCounter.generateViewId()
}

