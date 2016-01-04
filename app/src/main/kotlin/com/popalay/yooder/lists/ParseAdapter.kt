/*
package com.popalay.yooder.lists

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.parse.ParseObject
import com.popalay.yooder.R

class ParseAdapter(context: Context, val parseParent: ViewGroup) : RecyclerView.Adapter<ParseAdapter.ViewHolder>() {

    private val parseAdapter: ParseQueryAdapter<ParseObject>

    init {
        parseAdapter = object : ParseQueryAdapter<ParseObject>(context, "thing") {

            fun getItemView(`object`: ParseObject, v: View?, parent: ViewGroup): View {
                var v = v
                if (v == null) {
                    v = LayoutInflater.from(parent.context).inflate(R.layout.thing_card, parent, false)
                }
                super.getItemView(`object`, v, parent)

                val imageView = v!!.findViewById(R.id.icon) as ParseImageView
                imageView.setParseFile(`object`.getParseFile("thumbnail"))
                imageView.loadInBackground()

                val nameView = v.findViewById(R.id.thing_card_name) as TextView
                nameView.text = `object`.getString("name")
                return v
            }
        }
        parseAdapter.addOnQueryLoadListener(OnQueryLoadListener())
        parseAdapter.loadObjects()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParseAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.thing_card, parent, false)
        val vh = ViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        parseAdapter.getView(position, holder.cardView, parseParent)
    }

    override fun getItemCount(): Int {
        return parseAdapter.getCount()
    }

    class ViewHolder(var cardView: View) : RecyclerView.ViewHolder(cardView)

    inner class OnQueryLoadListener : ParseQueryAdapter.OnQueryLoadListener<ParseObject> {

        fun onLoading() {

        }

        fun onLoaded(objects: List<ParseObject>, e: Exception) {
            thingsAdapter.notifyDataSetChanged()
        }
    }
}
*/
