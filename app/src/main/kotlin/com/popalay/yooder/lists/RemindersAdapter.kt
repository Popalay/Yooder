package com.popalay.yooder.lists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.firebase.client.Query
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.models.Remind
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_remind.view.*
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
            message.text = items?.get(position)?.message
            dataManager.getUser(items?.get(position)?.from!!)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { user ->
                        name.text = user.name
                        Glide.with(context).load(user.photo).bitmapTransform(CropCircleTransformation(context)).into(photo)
                    }
        }
    }
}