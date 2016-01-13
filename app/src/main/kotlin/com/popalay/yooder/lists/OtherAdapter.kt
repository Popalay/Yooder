package com.popalay.yooder.lists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.dift.ui.SwipeToAction
import com.popalay.yooder.R
import com.popalay.yooder.models.Other
import kotlinx.android.synthetic.main.card_other.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.backgroundResource
import java.text.SimpleDateFormat
import java.util.*

class OtherAdapter : RecyclerView.Adapter<OtherAdapter.ViewHolder>(), AnkoLogger {

    var others: MutableList<Other> = ArrayList()

    override fun getItemId(position: Int): Long {
        return others[position].objectId.toLong()
    }

    override fun getItemCount(): Int {
        return others.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val other = others[position]
        with(holder.itemView) {
            titleReminder.text = other.title
            if(other.date != null) {
                var a = (Date().time - other.createdAt.time).toFloat()
                var b = ((other.date as Date).time - other.createdAt.time).toFloat()
                var p = a / b * 100
                when (p) {
                    in 0..25 -> progress.backgroundResource = R.color.green
                    in 26..50 -> progress.backgroundResource = R.color.yellow
                    in 51..75 -> progress.backgroundResource = R.color.orange
                    else -> progress.backgroundResource = R.color.red
                }
                date.visibility = View.VISIBLE
                date.text = SimpleDateFormat("dd.MM.yyyy HH:mm ").format(other.date)
            } else {
                progress.backgroundResource = R.color.jumbo
                date.visibility = View.GONE
            }
            description.text = other.description
        }
        holder.data = other
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.card_other_default, null)
        return ViewHolder(view)
    }

    class ViewHolder(var viewItem: View) : SwipeToAction.ViewHolder<Other>(viewItem)
}