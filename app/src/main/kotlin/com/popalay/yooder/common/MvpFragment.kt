package com.popalay.yooder.common

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpDelegate


class MvpFragment : Fragment() {

    val mvpDelegate: MvpDelegate<MvpFragment> by lazy {
        MvpDelegate(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate.onStart()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onStop()
    }
}