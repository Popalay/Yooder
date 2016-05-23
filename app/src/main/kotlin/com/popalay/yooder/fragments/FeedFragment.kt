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
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_recycler.*
import javax.inject.Inject

class FeedFragment : BaseFragment() {

    @Inject lateinit var dataManager: DataManager
    @Inject lateinit var socialManager: SocialManager

    init {
        Application.graph.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_recycler, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.setHasFixedSize(true)
        VerticalDividerItemDecoration.Builder(context).build()
        recycler.addItemDecoration(HorizontalDividerItemDecoration.Builder(context)
                .marginResId(R.dimen.divider_margin_left, R.dimen.divider_margin_right)
                .colorResId(R.color.primaryDark)
                .build())
        val adapter = RemindersAdapter(dataManager.getMyRemindersQuery(socialManager.getMyId()))
/*        recycler.setOnItemClickListener { position ->
            d("click")
            val isExpanded = position == adapter.mExpandedPosition
            adapter.mExpandedPosition = if(isExpanded) -1 else position
            //TransitionManager.beginDelayedTransition(recycler)
            //adapter.notifyItemInserted(adapter.mExpandedPosition + 1)
            adapter.notifyDataSetChanged()
        }*/
        recycler.adapter = adapter
    }
}


