package com.bitspilanidvm.bosm2017

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    init {
        var itemText = itemView.findViewById<TextView>(R.id.itemText)
        var itemImage = itemView.findViewById<ImageView>(R.id.itemImage)
    }
}