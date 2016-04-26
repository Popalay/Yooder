package com.popalay.yooder.lists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.firebase.client.Query
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.extensions.toRelatedDate
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.models.Remind
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_remind.view.*
import kotlinx.android.synthetic.main.view_remind_front.view.*
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class RemindersAdapter(query: Query) : BaseFirebaseAdapter<Remind>(query, Remind::class.java) {

    @Inject lateinit var dataManager: DataManager

    init {
        Application.graph.inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_remind, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView) {
            time.text = items[position].time.toRelatedDate()
            message.text = items[position].message
            dataManager.getUser(items[position].to)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { user ->
                        name.text = user.name
                        Glide.with(context).load(user.photo).bitmapTransform(CropCircleTransformation(context)).into(photo)
                    }
            folding_cell.initialize(500, context.resources.getColor(R.color.primaryDark), 0);
            folding_cell.onClick {
                folding_cell.toggle(false)
            }
        }
    }
}