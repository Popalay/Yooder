package com.popalay.yooder.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.pwittchen.reactivenetwork.library.ConnectivityStatus
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork
import com.parse.ParseObject
import com.parse.ParseUser
import com.popalay.yooder.R
import com.popalay.yooder.eventbus.AddedDebtEvent
import com.popalay.yooder.eventbus.BusProvider
import com.popalay.yooder.lists.DebtAdapter
import com.popalay.yooder.models.Debt
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.tab_fragment_debts.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.onRefresh
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

public class DebtsTabFragment : Fragment(), AnkoLogger {

    var adapter: DebtAdapter = DebtAdapter()
    var status = false
    var subscription: Subscription? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(R.layout.tab_fragment_debts, container, false)
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
    }

    fun load() {
        info("load")
        Debt.getByAuthor(ParseUser.getCurrentUser()).findInBackground { debts, e ->
            if (e == null) {
                ParseObject.unpinAllInBackground {
                    ParseObject.pinAll(debts)
                }
                adapter.debts = debts
                adapter.notifyDataSetChanged()
                info(debts.size)
            }else{
                error(e.message!!)
            }
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
        }
    }

    fun loadFromLocal() {
        info("loadFromLocal")
        Debt.getByAuthor(ParseUser.getCurrentUser()).fromLocalDatastore().findInBackground { debts, e ->
            if (e == null) {
                adapter.debts = debts
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
    public fun onAddedDebt(event: AddedDebtEvent) {
        info("added")
        adapter.debts.add(event.debt)
        adapter.notifyDataSetChanged()
    }
}

