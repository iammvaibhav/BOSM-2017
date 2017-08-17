package com.bitspilanidvm.bosm2017

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class ViewHolder_MainItem(itemView: View) : RecyclerView.ViewHolder(itemView){
    var itemText: TextView
    var itemImage: ImageView
    init {
        itemText = itemView.findViewById(R.id.itemText)
        itemImage = itemView.findViewById(R.id.itemImage)
    }
}