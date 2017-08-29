package com.bitspilanidvm.bosm2017.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.ClickListeners.DetailsRecyclerViewClickListener
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.Universal.GLOBAL_DATA
import com.bitspilanidvm.bosm2017.ViewHolder.DetailedItem

class DetailsRecyclerView(val headings: ArrayList<String>, val details: ArrayList<String>, val listener: DetailsRecyclerViewClickListener) : RecyclerView.Adapter<DetailedItem>(){

    override fun onBindViewHolder(holder: DetailedItem, position: Int) {
        //GLOBAL_DATA.sportsMapReverse[headings[position]]
        holder.imageView.setImageResource(GLOBAL_DATA.imageRes1[0])
        holder.titleTextView.text = headings[position]
        holder.detailsTextView.text = details[position]
        holder.itemView.setOnClickListener { listener.onItemClick(holder, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DetailedItem {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_detail_recycler_view, parent, false)
        return DetailedItem(itemView)
    }

    override fun getItemCount() = headings.size
}