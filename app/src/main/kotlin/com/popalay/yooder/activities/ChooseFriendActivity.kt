package com.popalay.yooder.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.lists.FriendsAdapter
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.managers.SocialManager
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_choose_friend.*
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class ChooseFriendActivity : BaseActivity() {

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_friend)
        Application.graph.inject(this)
        // Get current user
        initUI()
    }

    private fun initUI() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Choose friend"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initList()
    }


    private fun initList() {
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)
        val adapter = FriendsAdapter()
        recycler.adapter = adapter
        dataManager.getFriends(socialManager.getMyId())
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    adapter.items.add(it)
                    adapter.notifyItemInserted(adapter.items.size - 1)
                }
    }

}