package com.bitspilanidvm.bosm2017.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.Universal.EventItem
import com.bitspilanidvm.bosm2017.R

class EventItem(val item: EventItem) : RecyclerView.Adapter<com.bitspilanidvm.bosm2017.ViewHolder.EventItem>(){
    override fun onBindViewHolder(holder: com.bitspilanidvm.bosm2017.ViewHolder.EventItem, position: Int) {
        holder.imageView.setImageResource(item.imageRes)
        holder.heading.text = item.heading
        holder.time.text = item.time
        holder.details.text = item.details
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.bitspilanidvm.bosm2017.ViewHolder.EventItem {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item_layout, parent, false)
        return com.bitspilanidvm.bosm2017.ViewHolder.EventItem(view)
    }

    override fun getItemCount() = 1
}