package com.popalay.yooder.extensions

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.backgroundColor

fun Fragment.hideKeyboard() {
    if (activity != null && activity.window != null && activity.window.decorView != null) {
        var imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE)as InputMethodManager ;
        imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0);
    }
}

fun View.animateRevealShow(@ColorInt to: Int) {
    this.backgroundColor = to
    val cx = (this.left + this.right) / 2
    val cy = (this.top + this.bottom) / 2
    val finalRadius = Math.max(this.width, this.height).toFloat()

    val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius)
    this.visibility = View.VISIBLE
    anim.duration = 300
    anim.interpolator = AccelerateInterpolator()
    anim.start()
}

fun Activity.animateSystemViews(@ColorInt color: Int) {
    val from = window.navigationBarColor;
    val to = color

    val colorAnimation = ValueAnimator.ofArgb(from, to);
    colorAnimation.addUpdateListener { animator ->
        window.navigationBarColor = animator.animatedValue as Int
        window.statusBarColor = animator.animatedValue as Int
    }
    colorAnimation.duration = 300
    colorAnimation.start();
}