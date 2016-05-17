package com.popalay.yooder.widgets

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment = mFragmentList[position]

    override fun getCount(): Int = mFragmentList.size

    fun addFrag(fragment: Fragment, title: String = "") {
        mFragmentList.add(fragment)
        mTitleList.add(title)
    }

    override fun getPageTitle(position: Int) = mTitleList[position]
}