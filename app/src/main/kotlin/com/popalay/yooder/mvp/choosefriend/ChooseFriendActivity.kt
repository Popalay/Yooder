package com.popalay.yooder.mvp.choosefriend

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.startActivity
import com.popalay.yooder.R
import com.popalay.yooder.activities.BaseActivity
import com.popalay.yooder.lists.FriendsAdapter
import com.popalay.yooder.models.User
import com.popalay.yooder.widgets.setOnItemClickListener
import kotlinx.android.synthetic.main.activity_choose_friend.*

class ChooseFriendActivity : BaseActivity(), ChooseFriendView {

    @InjectPresenter lateinit var presenter1: ChooseFriendPresenter

    lateinit var adapter: FriendsAdapter

    companion object {
        fun open(context: Context) {
            context.startActivity<ChooseFriendActivity>()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_friend)
        initUI()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
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
        adapter = FriendsAdapter()
        recycler.adapter = adapter
        recycler.setOnItemClickListener { position ->
            presenter1.friendChosen(this, adapter.items[position])
        }
    }

    override fun attachFriends(friends: List<User>) {
        adapter.items = friends
        adapter.notifyDataSetChanged()
    }

    override fun onRemindCreated() {

    }
}
