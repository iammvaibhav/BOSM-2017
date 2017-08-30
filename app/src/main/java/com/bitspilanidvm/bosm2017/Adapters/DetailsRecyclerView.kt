package com.bitspilanidvm.bosm2017.Adapters

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bitspilanidvm.bosm2017.ClickListeners.DetailsRecyclerViewClickListener
import com.bitspilanidvm.bosm2017.R
import com.bitspilanidvm.bosm2017.Universal.GLOBAL_DATA
import com.bitspilanidvm.bosm2017.ViewHolder.DetailedItem
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator

class DetailsRecyclerView(val headings: ArrayList<String>, val details: ArrayList<String>, val listener: DetailsRecyclerViewClickListener, val context: Context) : RecyclerView.Adapter<DetailedItem>(){

    val typeface = Typeface.createFromAsset(context.assets, "fonts/Coves-Bold.otf")

    override fun onBindViewHolder(holder: DetailedItem, position: Int) {
        //holder.imageView.setImageResource(GLOBAL_DATA.sportsImageRes[headings[position]] ?: android.R.color.holo_blue_light)
        picasso(context, GLOBAL_DATA.sportsImageRes[headings[position]] ?: R.mipmap.ic_launcher).into(holder.imageView)
        holder.titleTextView.text = headings[position]
        holder.detailsTextView.text = details[position]
        holder.titleTextView.typeface = typeface
        holder.detailsTextView.typeface = typeface
        holder.itemView.setOnClickListener { listener.onItemClick(holder, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DetailedItem {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_detail_recycler_view, parent, false)
        return DetailedItem(itemView)
    }

    override fun getItemCount() = headings.size

    fun picasso(context: Context, resourceId: Int): RequestCreator {
        return Picasso.with(context)
                .load(resourceId)
                .fit()
                .centerCrop()
    }
}