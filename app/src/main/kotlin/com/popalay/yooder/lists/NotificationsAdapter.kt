package com.popalay.yooder.lists

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.database.Query
import com.popalay.yooder.Application
import com.popalay.yooder.R
import com.popalay.yooder.extensions.toRelatedDate
import com.popalay.yooder.managers.DataManager
import com.popalay.yooder.models.Remind
import kotlinx.android.synthetic.main.view_remind_front.view.*
import javax.inject.Inject


class NotificationsAdapter(query: Query) : BaseFirebaseAdapter<Remind>(query, Remind::class.java) {

    @Inject lateinit var dataManager: DataManager

    init {
        Application.getGraph().inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_remind, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        with(holder.itemView) {
            time.text = items[position].time.toRelatedDate()
            message.text = items[position].message
/*            message_full.text = items[position].message
            dataManager.getUser(items[position].from)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { user ->
                        name.text = user.name
                        Glide.with(context).load(user.photo).bitmapTransform(CropCircleTransformation(context)).into(photo)
                    }*/
            val priority = items[position].priority
            val color: Int;
            when (priority) {
                1 -> color = R.color.green
                2 -> color = R.color.yellow
                3 -> color = R.color.orange
                4 -> color = R.color.red
                else -> color = R.color.card
            }
            priorityBg.setColorFilter(ContextCompat.getColor(context, color))
        }
    }
}