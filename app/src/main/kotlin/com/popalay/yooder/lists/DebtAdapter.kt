package com.popalay.yooder.lists

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.dift.ui.SwipeToAction
import com.popalay.yooder.R
import com.popalay.yooder.models.Debt
import kotlinx.android.synthetic.main.card_debt.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.backgroundResource
import java.text.SimpleDateFormat
import java.util.*

class DebtAdapter : RecyclerView.Adapter<DebtAdapter.DebtViewHolder>(), AnkoLogger {

    var debts: MutableList<Debt> = ArrayList()

    override fun getItemId(position: Int): Long {
        return debts[position].objectId.toLong()
    }

    override fun getItemCount(): Int {
        return debts.size
    }

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        val debt = debts[position]
        with(holder.itemView) {
            amount.text = debt.amount.toString()
            party.text = (if (debt.isDebtor) "from " else "to ") + debt.party
            if(debt.date != null) {
                var a = (Date().time - debt.createdAt.time).toFloat()
                var b = ((debt.date as Date).time - debt.createdAt.time).toFloat()
                var p = a / b * 100
                when (p) {
                    in 0..25 -> progress.backgroundResource = R.color.green
                    in 26..50 -> progress.backgroundResource = R.color.yellow
                    in 51..75 -> progress.backgroundResource = R.color.orange
                    else -> progress.backgroundResource = R.color.red
                }
                date.visibility = View.VISIBLE
                date.text = "to " + SimpleDateFormat("dd.MM.yyyy HH:mm ").format(debt.date)
            } else {
                progress.backgroundResource = R.color.jumbo
                date.visibility = View.GONE
            }
            description.text = debt.description
        }
        holder.data = debt
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DebtViewHolder? {
        var view = LayoutInflater.from(parent?.context).inflate(R.layout.card_debt_default, null)
        return DebtViewHolder(view)
    }

    class DebtViewHolder(var viewItem: View) : SwipeToAction.ViewHolder<Debt>(viewItem)
}