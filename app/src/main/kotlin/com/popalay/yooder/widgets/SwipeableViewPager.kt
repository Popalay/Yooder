package com.popalay.yooder.widgets

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class SwipeableViewPager : ViewPager {

    var isEnabledSwipe = true

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isEnabledSwipe && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isEnabledSwipe && super.onInterceptTouchEvent(event)
    }
}
