package com.popalay.yooder.lists

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query

import java.util.*

abstract class FirebaseRecyclerAdapter<ViewHolder : RecyclerView.ViewHolder, T>(private val mQuery: Query, private val mItemClass: Class<T>,
                                                                                var items: ArrayList<T> = ArrayList(),
                                                                                var keys: ArrayList<String> = ArrayList()) : RecyclerView.Adapter<ViewHolder>() {

    private val mListener = object : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
            val key = dataSnapshot.key

            if (!keys.contains(key)) {
                val item = dataSnapshot.getValue(this@FirebaseRecyclerAdapter.mItemClass)
                val insertedPosition: Int
                if (previousChildName == null) {
                    items.add(0, item)
                    keys.add(0, key)
                    insertedPosition = 0
                } else {
                    val previousIndex = keys.indexOf(previousChildName)
                    val nextIndex = previousIndex + 1
                    if (nextIndex == items.size) {
                        items.add(item)
                        keys.add(key)
                    } else {
                        items.add(nextIndex, item)
                        keys.add(nextIndex, key)
                    }
                    insertedPosition = nextIndex
                }
                notifyItemInserted(insertedPosition)
                itemAdded(item, key, insertedPosition)
            }
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {
            val key = dataSnapshot.key

            if (keys.contains(key)) {
                val index = keys.indexOf(key)
                val oldItem = items[index]
                val newItem = dataSnapshot.getValue(this@FirebaseRecyclerAdapter.mItemClass)

                items[index] = newItem

                notifyItemChanged(index)
                itemChanged(oldItem, newItem, key, index)
            }
        }

        override fun onChildRemoved(dataSnapshot: DataSnapshot) {
            val key = dataSnapshot.key

            if (keys.contains(key)) {
                val index = keys.indexOf(key)
                val item = items[index]

                keys.removeAt(index)
                items.removeAt(index)

                notifyItemRemoved(index)
                itemRemoved(item, key, index)
            }
        }

        override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
            val key = dataSnapshot.key

            val index = keys.indexOf(key)
            val item = dataSnapshot.getValue(this@FirebaseRecyclerAdapter.mItemClass)
            items.removeAt(index)
            keys.removeAt(index)
            val newPosition: Int
            if (previousChildName == null) {
                items.add(0, item)
                keys.add(0, key)
                newPosition = 0
            } else {
                val previousIndex = keys.indexOf(previousChildName)
                val nextIndex = previousIndex + 1
                if (nextIndex == items.size) {
                    items.add(item)
                    keys.add(key)
                } else {
                    items.add(nextIndex, item)
                    keys.add(nextIndex, key)
                }
                newPosition = nextIndex
            }
            notifyItemMoved(index, newPosition)
            itemMoved(item, key, index, newPosition)
        }

        override fun onCancelled(firebaseError: DatabaseError) {
            Log.e("FirebaseListAdapter", "Listen was cancelled, no more updates will occur.")
        }
    }

    init {
        mQuery.addChildEventListener(mListener)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder

    abstract override fun onBindViewHolder(holder: ViewHolder, position: Int)

    override fun getItemCount(): Int {
        return items.size
    }

    fun destroy() {
        mQuery.removeEventListener(mListener)
    }

    fun getItem(position: Int): T {
        return items[position]
    }

    fun getPositionForItem(item: T): Int {
        return if (items.size > 0) items.indexOf(item) else -1
    }

    operator fun contains(item: T): Boolean {
        return items.contains(item)
    }

    protected abstract fun itemAdded(item: T, key: String, position: Int)

    protected abstract fun itemChanged(oldItem: T, newItem: T, key: String, position: Int)

    protected abstract fun itemRemoved(item: T, key: String, position: Int)

    protected abstract fun itemMoved(item: T, key: String, oldPosition: Int, newPosition: Int)

}
