package com.bitspilanidvm.bosm2017

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class Adapter_DetailsRecyclerView(val imageRes: Array<Int>, val headings: Array<String>, val details: Array<String>, val listener: DetailsRecyclerViewClickListener) : RecyclerView.Adapter<ViewHolder_DetailItem>(){

    override fun onBindViewHolder(holder: ViewHolder_DetailItem?, position: Int) {
        holder?.imageView?.setImageResource(imageRes[position])
        holder?.titleTextView?.text = headings[position]
        holder?.detailsTextView?.text = details[position]
        holder?.itemView?.setOnClickListener { listener.onItemClick(holder, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder_DetailItem {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_detail_recycler_view, parent, false)
        return ViewHolder_DetailItem(itemView)
    }

    override fun getItemCount() = headings.size
}