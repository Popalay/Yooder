package com.popalay.yooder.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.dift.ui.SwipeToAction
import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork
import com.parse.ParseObject
import com.parse.ParseUser
import com.popalay.yooder.R
import com.popalay.yooder.eventbus.AddedOtherEvent
import com.popalay.yooder.eventbus.BusProvider
import com.popalay.yooder.lists.OtherAdapter
import com.popalay.yooder.models.Other
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.tab_fragment_others.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.onRefresh
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

public class OthersTabFragment : Fragment(), AnkoLogger {

    var adapter: OtherAdapter = OtherAdapter()
    var status = false
    var subscription: Subscription? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(R.layout.tab_fragment_others, container, false)
        BusProvider.bus.register(this)
        subscription = ReactiveNetwork().observeConnectivity(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { connectivityStatus ->
                    if (connectivityStatus == ConnectivityStatus.OFFLINE) {
                        if (!status) loadFromLocal()
                        status = false
                    } else {
                        if (!status) load()
                        status = true
                    }
                    info(status)
                }
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh.setColorSchemeResources(R.color.accent)
        swipeRefresh.onRefresh {
            if (status) load() else swipeRefresh.isRefreshing = false
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        val swipeToAction = SwipeToAction(recycler, object : SwipeToAction.SwipeListener<Other> {

            override fun onLongClick(itemData: Other) {

            }

            override fun swipeLeft(itemData: Other): Boolean {
                return true;
            }

            override fun swipeRight(itemData: Other): Boolean {
                val index = adapter.others.indexOf(itemData)
                adapter.others.remove(itemData)
                adapter.notifyItemRemoved(index)
                Snackbar.make(getView(), "${itemData.title} removed", Snackbar.LENGTH_LONG).setAction("Undo") {
                    adapter.others.add(index, itemData)
                    adapter.notifyItemInserted(index)
                }.show()
                return true
            }

            override fun onClick(itemData: Other) {
            }
        });
    }

    fun load() {
        info("load")
        Other.getByAuthor(ParseUser.getCurrentUser()).findInBackground { others, e ->
            if (e == null) {
                ParseObject.unpinAllInBackground("others") {
                    ParseObject.pinAll("others", others)
                }
                adapter.others = others
                adapter.notifyDataSetChanged()
                info(others.size)
            } else {
                error(e.message!!)
            }
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
        }
    }

    fun loadFromLocal() {
        info("loadFromLocal")
        Other.getByAuthor(ParseUser.getCurrentUser()).fromPin("others").findInBackground { others, e ->
            if (e == null) {
                adapter.others = others
                adapter.notifyDataSetChanged()
                debug(adapter.itemCount.toString())
            }
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        subscription?.unsubscribe()
        BusProvider.bus.unregister(this)
    }

    @Subscribe
    public fun onOtherAdded(event: AddedOtherEvent) {
        info("added")
        adapter.others.add(event.other)
        adapter.notifyDataSetChanged()
    }
}