package com.popalay.yooder.lists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.popalay.yooder.R
import com.popalay.yooder.models.User
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import jp.wasabeef.glide.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_user.view.*

class FriendsAdapter : RecyclerView.Adapter<UserViewHolder>(), FastScrollRecyclerView.SectionedAdapter {

    var items = emptyList<User>()

    override fun getSectionName(position: Int): String {
        return items[position].name[0].toString()
    }

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.card_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder.itemView) {
            name.text = items[position].name
            Glide.with(context).load(items[position].photo).bitmapTransform(CropCircleTransformation(context)).into(photo)
        }
    }

}
