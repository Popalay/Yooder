package com.popalay.yooder.lists

import android.support.v7.widget.RecyclerView
import com.google.firebase.database.Query


abstract class BaseFirebaseAdapter<T>(query: Query, classItem: Class<T>) : FirebaseRecyclerAdapter<RecyclerView.ViewHolder, T>(query, classItem) {

    override fun itemAdded(item: T, key: String, position: Int) {
    }

    override fun itemChanged(oldItem: T, newItem: T, key: String, position: Int) {
    }

    override fun itemRemoved(item: T, key: String, position: Int) {
    }

    override fun itemMoved(item: T, key: String, oldPosition: Int, newPosition: Int) {
    }
}