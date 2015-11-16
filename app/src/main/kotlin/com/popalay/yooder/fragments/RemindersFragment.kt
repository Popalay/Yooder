package com.popalay.yooder.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.clans.fab.FloatingActionButton
import com.popalay.yooder.R
import com.popalay.yooder.widgets.PagerAdapter
import kotlinx.android.synthetic.fragment_reminders.*

public class RemindersFragment : Fragment() {

    companion object {
        val TAG = "RemindersFragment"
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_reminders, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDebtFab.setOnClickListener {
            floatMenu.close(true)
            //AddDebtFragmentDialog.instance().show(activity.supportFragmentManager, AddDebtFragmentDialog.TAG)
        }
        addOtherFab.setOnClickListener {
            floatMenu.close(true)
        }
        initTabs()
    }

    private fun initTabs() {
        // Setup the viewPager
        val pagerAdapter = PagerAdapter(activity.supportFragmentManager)
        pagerAdapter.addFrag(DebtsTabFragment(), "Debts")
        pagerAdapter.addFrag(OthersTabFragment(), "Others")
        viewPager.adapter = pagerAdapter

        // Setup the Tabs
        tabLayout.setTabsFromPagerAdapter(pagerAdapter)
        // This method ensures that tab selection events update the ViewPager and page changes update the selected tab.
        tabLayout.setupWithViewPager(viewPager)
    }
}

