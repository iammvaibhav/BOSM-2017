package com.bitspilanidvm.bosm2017

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class Adapter_MainRecyclerView(var dataList: List<Pair<String, Int>>, val listener: MainRecyclerViewClickListener) : RecyclerView.Adapter<ViewHolder_MainItem>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder_MainItem {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_main_nav_recycler_view, parent, false)
        return ViewHolder_MainItem(view)
    }

    override fun onBindViewHolder(holder: ViewHolder_MainItem?, position: Int) {
        holder?.itemText?.text = dataList[position].first
        holder?.itemImage?.setImageResource(dataList[position].second)
        holder?.itemView?.setOnClickListener { listener.onItemClick(holder, position) }
    }

    override fun getItemCount() = dataList.size
}