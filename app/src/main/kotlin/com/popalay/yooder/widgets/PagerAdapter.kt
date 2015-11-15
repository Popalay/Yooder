package com.popalay.yooder.widgets

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*

public class PagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()

    override public fun getItem(position: Int): Fragment = mFragmentList[position]

    override public fun getCount(): Int = mFragmentList.size

    public fun addFrag(fragment: Fragment, title: String) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    override public fun getPageTitle(position: Int): CharSequence = mFragmentTitleList[position]
}