package com.popalay.yooder.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parse.ParseUser
import com.popalay.yooder.R
import com.popalay.yooder.eventbus.AddedDebtEvent
import com.popalay.yooder.eventbus.BusProvider
import com.popalay.yooder.lists.DebtAdapter
import com.popalay.yooder.models.Debt
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.tab_fragment_debts.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

public class DebtsTabFragment : Fragment(), AnkoLogger {

    var adapter: DebtAdapter = DebtAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater?.inflate(R.layout.tab_fragment_debts, container, false)
        BusProvider.bus.register(this)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        recycler.adapter = adapter
        Debt.getByAuthor(ParseUser.getCurrentUser()).findInBackground { debts, e ->
            if (e == null) {
                adapter.debts = debts
                adapter.notifyDataSetChanged()
                Log.d("ss", adapter.itemCount.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        BusProvider.bus.unregister(this)
    }

    @Subscribe
    public fun onAddedDebt(event: AddedDebtEvent) {
        info("added")
        adapter.debts.add(event.debt)
        adapter.notifyDataSetChanged()
    }
}