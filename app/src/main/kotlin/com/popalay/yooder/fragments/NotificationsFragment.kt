package com.popalay.yooder.fragments


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.lists.RemindersAdapter
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.SocialManager
import kotlinx.android.synthetic.main.fragment_notifications.*
import javax.inject.Inject

class NotificationsFragment : BaseFragment() {

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager

    init{
        Application.graph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_notifications, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        val adapter = RemindersAdapter(dataManager.getMyRemindersQuery(socialManager.getMyId()))
        recycler.adapter = adapter
    }
}


