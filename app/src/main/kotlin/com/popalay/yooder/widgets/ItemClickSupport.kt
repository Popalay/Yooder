package com.popalay.yooder.widgets

import android.support.v7.widget.RecyclerView
import android.view.View
import com.popalay.yooder.R

class ItemClickSupport(private val mRecyclerView: RecyclerView) {
    private var mOnItemClickListener: ((Int) -> Unit)? = null
    private var mOnItemLongClickListener: ((Int) -> Boolean)? = null

    private val mOnClickListener = View.OnClickListener { v ->
        val holder = mRecyclerView.getChildViewHolder(v)
        mOnItemClickListener?.invoke(holder.adapterPosition)
    }

    private val mOnLongClickListener = View.OnLongClickListener { v ->
        if (mOnItemLongClickListener != null) {
            val holder = mRecyclerView.getChildViewHolder(v)
            return@OnLongClickListener mOnItemLongClickListener!!.invoke(holder.adapterPosition)
        }
        false
    }
    private val mAttachListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener)
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener)
            }
        }

        override fun onChildViewDetachedFromWindow(view: View) {

        }
    }

    init {
        mRecyclerView.setTag(R.id.item_click_support, this)
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener)
    }

    fun setOnItemClickListener(listener: (position: Int) -> Unit): ItemClickSupport {
        mOnItemClickListener = listener
        return this
    }

    fun setOnItemLongClickListener(listener: (position: Int) -> Boolean): ItemClickSupport {
        mOnItemLongClickListener = listener
        return this
    }

    fun detach(view: RecyclerView) {
        view.removeOnChildAttachStateChangeListener(mAttachListener)
        view.setTag(R.id.item_click_support, null)
    }

}

fun RecyclerView.setOnItemClickListener(listener: (position: Int) -> Unit): ItemClickSupport {
    var support: ItemClickSupport? = this.getTag(R.id.item_click_support) as ItemClickSupport?
    if (support == null) {
        support = ItemClickSupport(this)
    }
    support.setOnItemClickListener(listener)
    return support
}

fun RecyclerView.setOnItemLongClickListener(listener: (position: Int) -> Boolean): ItemClickSupport {
    var support: ItemClickSupport? = this.getTag(R.id.item_click_support) as ItemClickSupport
    if (support == null) {
        support = ItemClickSupport(this)
    }
    support.setOnItemLongClickListener(listener)
    return support
}

fun RecyclerView.removeItemClickSupport(): ItemClickSupport {
    val support = this.getTag(R.id.item_click_support) as ItemClickSupport
    support.detach(this)
    return support
}

